package com.example.newsapp.data.models

data class ArticleItem(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: SourceX,
    val title: String,
    val url: String,
    val urlToImage: String
)