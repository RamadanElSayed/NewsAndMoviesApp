package com.example.newsapp.data.models

data class UserProfile(
    val id: Int,                 // Unique identifier for the user profile
    val name: String,            // Full name of the user
    val email: String,           // Email address
    val age: Int?,               // Optional age of the user
    val city: String?,           // Optional city of residence
    val job: String?,            // Job title or occupation
    val bio: String?,            // Short bio or description
    val profilePictureUrl: String?, // URL to the user's profile picture
    val followersCount: Int?,    // Optional count of followers
    val followingCount: Int?,    // Optional count of following users
    val posts: List<Post>?       // Optional list of posts by the user
)

data class Post(
    val id: Int,               // Unique identifier for the post
    val userId: Int,           // ID of the user who created the post
    val content: String,       // The main text or content of the post
    val imageUrl: String?,     // Optional URL to an image associated with the post
    val createdAt: String,     // Timestamp of when the post was created
    val likesCount: Int = 0,   // Number of likes
    val commentsCount: Int = 0 // Number of comments on the post
)
