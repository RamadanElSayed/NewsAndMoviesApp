package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.Article
import com.example.newsapp.util.Resource2
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getTopHeadline(
        category: String
    ): Flow<Resource2<List<Article>>>?
    suspend fun searchForNews(
        query: String
    ):Flow<Resource2<List<Article>>>

}