package com.example.newsapp.data.remote

import com.example.newsapp.BuildConfig
import com.example.newsapp.data.models.NewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    //https://newsapi.org/v2/top-headlines?country=us&apiKey=f0ef0040cf6b4bb49c85209accd11271

    @GET("top-headlines")
    suspend fun getBreakingNews(
        @Query("category") category: String,
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY

    ): Response<NewResponse>


    @GET("everything")
    suspend fun searchForNews(
        @Query("q") query: String,  // Default search query
        @Query("searchIn") searchIn: String? = "title,description",  // Default fields to search in
//        @Query("sources") sources: String? = null,  // No default source restriction
//        @Query("domains") domains: String? = null,  // No default domain restriction
//        @Query("excludeDomains") excludeDomains: String? = null,  // No default exclusion
//        @Query("from") from: String? = "2024-01-01",  // Default from date
//        @Query("to") to: String? = "2024-12-31",  // Default to date
        @Query("language") language: String? = "en",  // Default language is English
        @Query("sortBy") sortBy: String? = "publishedAt",  // Default sorting by published date
        @Query("apiKey") apiKey: String = BuildConfig.NEWS_API_KEY // Required API key, no default

    ): Response<NewResponse>

}