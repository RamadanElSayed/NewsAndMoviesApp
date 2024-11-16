package com.example.newsapp.data.repestory

import com.example.newsapp.data.models.Article
import com.example.newsapp.domain.repository.NewsRepository
import com.example.newsapp.util.Resource2
import com.example.newsapp.data.remote.NewsApiService
import javax.inject.Inject

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class NewsRepositoryImpl @Inject constructor(
    private val newsApiService: NewsApiService
) : NewsRepository {

    override suspend fun getTopHeadline(category: String): Flow<Resource2<List<Article>>> = flow {
        try {
            val response = newsApiService.getBreakingNews(category = category)
            if (response.isSuccessful) {
                emit(Resource2.Success(response.body()?.articles ?: emptyList())) // Emit success state
            } else {
                emit(Resource2.Error(message = "Error ${response.code()}: ${response.message()}")) // Emit error for unsuccessful response
            }
        } catch (exception: Exception) {
            emit(Resource2.Error(message = "Failed to fetch news: ${exception.message}")) // Emit error for caught exceptions
        }
    }.catch { exception ->
        // Handle any unexpected exceptions during Flow execution
        emit(Resource2.Error(message = "An unexpected error occurred: ${exception.message}"))
    }

    override suspend fun searchForNews(query: String): Flow<Resource2<List<Article>>> = flow {
        try {
            val response = newsApiService.searchForNews(query = query)
            if (response.isSuccessful) {
                emit(Resource2.Success(response.body()?.articles ?: emptyList())) // Emit success state
            } else {
                emit(Resource2.Error(message = "Error ${response.code()}: ${response.message()}")) // Emit error for unsuccessful response
            }
        } catch (exception: Exception) {
            emit(Resource2.Error(message = "Failed to fetch news: ${exception.message}")) // Emit error for caught exceptions
        }
    }.catch { exception ->
        // Handle any unexpected exceptions during Flow execution
        emit(Resource2.Error(message = "An unexpected error occurred: ${exception.message}"))
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