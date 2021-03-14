package com.example.myapplication

data class Post(
    val title: String,
    val text: String,
    val subreddit: String,
    val imgUrl: String?,
    val upvotes: Int,
    val num_comments: Int)