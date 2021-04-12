package com.example.myapplication

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import java.sql.Time
import java.util.*

class AppRunningService : Service() {
    companion object {
        const val APP_RUNNING_NAME = "12345"
        const val APP_RUNNING_ID = "23456"
    }

    override fun onStartCommand( intent: Intent?, flags: Int, startId: Int ): Int {
        notification()

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }

    private fun notification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel( APP_RUNNING_NAME, APP_RUNNING_NAME, NotificationManager.IMPORTANCE_DEFAULT )
            val manager = getSystemService( NOTIFICATION_SERVICE ) as NotificationManager
            manager.createNotificationChannel(channel)
            val builder = Notification.Builder(this, APP_RUNNING_NAME )
                    .setContentTitle( "App Running" )
                    .setContentText( "Click to go back to Feed" )
                    .setAutoCancel( true )
                    .setColor(188)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.drawable.ic_round_splash)
            val backToAppIntent = Intent (this, FeedActivity::class.java )
            val pendingAppNotificationIntent = PendingIntent.getService(this, 0, backToAppIntent, 0)
            val backToAppAction = Notification.Action.Builder( R.drawable.ic_round_splash,"App", pendingAppNotificationIntent ).build()
            builder.addAction( backToAppAction )
            val notify = builder.build()
            manager.notify( APP_RUNNING_ID.toInt(), notify )
        } else {
            makeNotification()
        }
    }

    //notifications for versions older than android 8
    private fun makeNotification() {
        val builder = Notification.Builder(this)
                .setContentTitle("title")
                .setContentText("text")
                .setAutoCancel(true)
                .setColor(188)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_round_splash)
        val backToAppIntent = Intent (this, FeedActivity::class.java)
        val pendingAppNotificationIntent = PendingIntent.getService(this, 0, backToAppIntent, 0)
        val backToAppAction = Notification.Action.Builder(R.drawable.ic_round_splash,"App", pendingAppNotificationIntent).build()
        builder.addAction(backToAppAction)
        val notify = builder.build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(APP_RUNNING_ID.toInt(), notify)
    }
}