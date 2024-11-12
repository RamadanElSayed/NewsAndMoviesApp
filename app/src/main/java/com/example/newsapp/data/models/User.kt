package com.example.newsapp.data.models

data class User(
    val id: Int,                // Unique identifier for the user
    val name: String,           // Name of the user
    val email: String,          // Email address of the user
    val age: Int? = null,       // Age of the user, optional
    val city: String? = null,   // City of residence, optional
    val job: String? = null     // Job title or occupation, optional
)