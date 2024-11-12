package com.example.newsapp.data.repestory

import com.example.newsapp.data.models.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Resource
import com.example.newsapp.data.remote.NewsApiService
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepository {
    override suspend fun getTopHeadline(category: String): Resource<List<Article>> {
        return try {
            val response = newsApiService.getBreakingNews(category = category)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.articles ?: emptyList())
            } else {
                Resource.Error(message = "Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news: ${e.message}")
        }
    }

    override suspend fun searchForNews(query: String): Resource<List<Article>> {
        return try {
            val response = newsApiService.searchForNews(query = query)
            if (response.isSuccessful) {
                Resource.Success(response.body()?.articles ?: emptyList())
            } else {
                Resource.Error(message = "Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news: ${e.message}")
        }
    }
}
/*
import retrofit2.Response

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getTopHeadline(category: String): Resource<List<Article>> {
        return try {
            val response = newsApiService.getBreakingNews(category = category)
            when (response.code()) {
                200 -> Resource.Success(response.body()?.articles ?: emptyList())
                404 -> Resource.Error(message = "Error 404: Not Found")
                500 -> Resource.Error(message = "Error 500: Internal Server Error")
                else -> Resource.Error(message = "Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news: ${e.message}")
        }
    }

    override suspend fun searchForNews(query: String): Resource<List<Article>> {
        return try {
            val response = newsApiService.searchForNews(query = query)
            when (response.code()) {
                200 -> Resource.Success(response.body()?.articles ?: emptyList())
                404 -> Resource.Error(message = "Error 404: Not Found")
                500 -> Resource.Error(message = "Error 500: Internal Server Error")
                else -> Resource.Error(message = "Error ${response.code()}: ${response.message()}")
            }
        } catch (e: Exception) {
            Resource.Error(message = "Failed to fetch news: ${e.message}")
        }
    }
}

 */