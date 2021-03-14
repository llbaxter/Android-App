package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val intent = Intent(this, FeedActivity::class.java)
        startActivity(intent)

//        val auth = FirebaseAuth.getInstance()
//
//        val login = findViewById<Button>(R.id.loginButton)
//        login.setOnClickListener {
//            //firebase
//            if (inputUsername.text.toString().isBlank()) {
//                Toast.makeText(this, "Enter a valid username", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//            if (inputPassword.text.toString().isBlank()) {
//                Toast.makeText(this, "Enter a valid password", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//
//            //sign in with email/password
//            auth.signInWithEmailAndPassword(inputUsername.text.toString(), inputPassword.text.toString()).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d("msg", auth.currentUser?.email.toString())
//                    val intent = Intent(this, FeedActivity::class.java)
//                    startActivity(intent)
//                } else {
//                    Log.d("msg2", auth.currentUser?.email.toString())
//                    Toast.makeText(this, "Enter a valid username and password", Toast.LENGTH_SHORT).show()
//                    inputUsername.text.clear()
//                    inputPassword.text.clear()
//                }
//            }
//        }
    }
}


