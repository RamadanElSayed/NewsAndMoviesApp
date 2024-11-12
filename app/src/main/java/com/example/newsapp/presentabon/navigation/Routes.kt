package com.example.newsapp.presentabon.navigation

import kotlinx.serialization.Serializable

// Define the routes
@Serializable
object NewsScreen

@Serializable
data class ArticleScreen(val webUrl: String)
