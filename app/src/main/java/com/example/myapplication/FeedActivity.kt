package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
            if (post.has("post_hint")) {
                val postHint = post.get("post_hint").asString
                Log.d("msg0", postHint)
                if (postHint == "image") {
                    imgUrl = post.get("url").asString
                } else if (postHint != "self") {
                    continue
                }
            }
            val upvotes = post.get("score").asInt
            val numComments = post.get("num_comments").asInt
//                Log.d("msg", title)
            val postObj = Post(title, text, subreddit, imgUrl, upvotes, numComments)
            listOfPosts.add(postObj)
            Log.d("msg1", title)
            Log.d("msg2", imgUrl?: text)
            Log.d("msg3", text)

        }
        return listOfPosts
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.options1, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        var subreddit: String = when (item.itemId) {
//            R.id.subAll -> "all"
//            R.id.subAskreddit -> "Askreddit"
//            R.id.subJokes -> "Jokes"
//            R.id.subNews -> "Jokes"
//            R.id.subPopular -> "Popular"
//            else -> "all"
//        }
        var sort: String = when (item.itemId) {
            R.id.sortHot -> "hot"
            R.id.sortControversial -> "controversial"
            R.id.sortNew -> "new"
            R.id.sortTop -> "top"
            else -> "hot"
        }

        val intent = Intent(this, FeedActivity::class.java)
        intent.putExtra("subreddit", "all")
        intent.putExtra("sort", sort)
        startActivity(intent)

        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)



        val subreddit = intent.getStringExtra("subreddit")!!
        val sort = intent.getStringExtra("sort")!!
        title = subreddit.substring(0, 1).toUpperCase() + subreddit.substring(1) + " - " + sort

        val intent = Intent(this, AppRunningService::class.java)
        startService(intent)


        Ion.with(this)
            .load("https://www.reddit.com/r/" + subreddit + "/" + sort +".json?limit=20")
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