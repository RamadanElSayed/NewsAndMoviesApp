package com.example.newsapp.util

sealed class Resource2<T>(val data: T?= null, val message:String? = null){
    class Success<T>(data: T?) : Resource2<T>(data=data)
    class Error<T>(message: String?) : Resource2<T>(message=message)
}


sealed class Resource<out T> {

    data class Success<out T>(val data: T?) : Resource<T>()

    data class Error(
        val exception: Exception,
        val message: String? = exception.message
    ) : Resource<Nothing>()

    data class ServerError(
        val errorContent: ServerErrorContent
    ) : Resource<Nothing>() {
        val status: String = errorContent.status
        val serverMessage: String = errorContent.serverMessage
        val statusCode: Int = errorContent.statusCode
    }

    object Loading : Resource<Nothing>()

    // Additional utility methods
    fun isSuccess(): Boolean = this is Success<T>
    fun isError(): Boolean = this is Error || this is ServerError
    fun isLoading(): Boolean = this is Loading
    fun getOrNull(): T? = if (this is Success) data else null
}

class ServerErrorContent(
    val status: String,
    val serverMessage: String,
    val statusCode: Int
) : Exception("$statusCode: $serverMessage")
