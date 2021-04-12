package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_feed.*
import kotlinx.android.synthetic.main.activity_login.*

class FeedActivity : AppCompatActivity() {
        fun getPosts(obj: JsonObject): MutableList<Post> {
        val listOfPosts = mutableListOf<Post>()
        val data = obj.getAsJsonObject("data")
        val posts = data.getAsJsonArray("children")
        for (i: Int in 0 until posts.size()) {
            val post = posts.get(i)
                .asJsonObject
                .getAsJsonObject("data")
            val title = post.get("title").asString
            val text = post.get("selftext").asString
            val subreddit = post.get("subreddit").asString
            var imgUrl: String? = null
            if (post.has("post_hint") && post.get("post_hint").asString == "image") {
                imgUrl = post.get("url").asString
            }
            val upvotes = post.get("score").asInt
            val numComments = post.get("num_comments").asInt
//                Log.d("msg", title)
            val postObj = Post(title, text, subreddit, imgUrl, upvotes, numComments)
            listOfPosts.add(postObj)
        }
        return listOfPosts
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        val intent = Intent(this, AppRunningService::class.java)
        startService(intent)


        Ion.with(this)
            .load("https://www.reddit.com/r/funny.json?limit=20")
            .asJsonObject()
            .setCallback { e, result ->
                val data = getPosts(result)
                val layoutManager = LinearLayoutManager(this)
                layoutManager.orientation = LinearLayoutManager.VERTICAL
                val adapter = FeedAdapter(this, data)
                rvFeed.layoutManager = layoutManager
                rvFeed.adapter = adapter
            }
    }
}