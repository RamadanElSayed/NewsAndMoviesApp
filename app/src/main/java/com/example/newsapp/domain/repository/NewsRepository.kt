package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.Article
import com.example.newsapp.util.Resource

interface NewsRepository {
    suspend fun getTopHeadline(
        category: String
    ):Resource<List<Article>>
    suspend fun searchForNews(
        query: String
    ):Resource<List<Article>>

}