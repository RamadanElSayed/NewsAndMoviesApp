package com.example.newsapp.data.repestory

import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.User
import com.example.newsapp.data.models.UserProfile
import com.example.newsapp.data.remote.ApiService
import com.example.newsapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject

// Resource class for handling API states
sealed class ResourceUsingDOAPIRequest<out T> {
    data class Success<out T>(val data: T?) : ResourceUsingDOAPIRequest<T>()
    data class Error(val exception: Throwable) : ResourceUsingDOAPIRequest<Nothing>()
    data class ServerError(val errorContent: ServerErrorContent) : ResourceUsingDOAPIRequest<Nothing>()
    data object Loading : ResourceUsingDOAPIRequest<Nothing>()
}

// Details for server errors
data class ServerErrorContent(
    val status: String,
    val serverMessage: String,
    val statusCode: Int
)

// Custom exception for network errors
class NetworkException(cause: Throwable) : Exception("Network Error: ${cause.message}", cause)

// Custom exception for unexpected errors
class UnexpectedException(cause: Throwable) : Exception("Unexpected Error: ${cause.message}", cause)

// Implementation of UserRepository
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override suspend fun getUserById(id: Int): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.getUserById(id) }

    override suspend fun createUser(user: User): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.createUser(user) }

    override suspend fun updateUser(id: Int, user: User): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.updateUser(id, user) }

    override suspend fun deleteUser(id: Int): Flow<ResourceUsingDOAPIRequest<Unit>> =
        safeApiCall { apiService.deleteUser(id) }

    override suspend fun getUsersByStatus(status: String): Flow<ResourceUsingDOAPIRequest<List<User>>> =
        safeApiCall { apiService.getUsersByStatus(status) }

    override suspend fun getUsersByFilters(filters: Map<String, String>): Flow<ResourceUsingDOAPIRequest<List<User>>> =
        safeApiCall { apiService.getUsersByFilters(filters) }

    override suspend fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<ResourceUsingDOAPIRequest<UploadResponse>> =
        safeApiCall { apiService.uploadImage(file, description) }

    override suspend fun getUsersByUrl(url: String): Flow<ResourceUsingDOAPIRequest<List<User>>> =
        safeApiCall { apiService.getUsersByUrl(url) }

    override suspend fun createUserWithFields(name: String, job: String): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.doCreateUserWithField(name, job) }

    override suspend fun getUserProfile(token: String): Flow<ResourceUsingDOAPIRequest<UserProfile>> =
        safeApiCall { apiService.getUserProfile(token) }

    override suspend fun getUserProfileWithHeaders(headers: Map<String, String>): Flow<ResourceUsingDOAPIRequest<UserProfile>> =
        safeApiCall { apiService.getUserProfileWithHeaders(headers) }

    override suspend fun registerUser(user: User): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.registerUser(user) }

    override suspend fun doCreateUserWithField(name: String, job: String): Flow<ResourceUsingDOAPIRequest<User>> =
        safeApiCall { apiService.doCreateUserWithField(name, job) }
}

// Centralized safe API call handler
suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Flow<ResourceUsingDOAPIRequest<T>> =
    flow {
        emit(ResourceUsingDOAPIRequest.Loading) // Emit loading state
        val response = apiCall()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                emit(ResourceUsingDOAPIRequest.Success(body)) // Emit success state
            } ?: emit(ResourceUsingDOAPIRequest.Error(Exception("Empty response body")))
        } else {
            emit(
                ResourceUsingDOAPIRequest.ServerError(
                    ServerErrorContent(
                        status = "Error",
                        serverMessage = response.message(),
                        statusCode = response.code()
                    )
                )
            )
        }
    }.catch { exception ->
        emit(handleApiError(exception)) // Handle and emit error state
    }

// Exception handling for API errors
private fun handleApiError(exception: Throwable): ResourceUsingDOAPIRequest<Nothing> {
    return when (exception) {
        is TimeoutException -> ResourceUsingDOAPIRequest.Error(NetworkException(exception))
        is IOException -> ResourceUsingDOAPIRequest.Error(NetworkException(exception))
        is HttpException -> {
            val statusCode = exception.code()
            val errorBody = exception.response()?.errorBody()?.string()
            val errorMessage = when (statusCode) {
                400 -> "Bad Request"
                401 -> "Unauthorized. Please check your credentials."
                403 -> "Forbidden. You do not have permission to access this resource."
                404 -> "Resource not found."
                500 -> "Internal Server Error. Please try again later."
                503 -> "Service Unavailable. Please try again later."
                else -> "Unexpected HTTP Error: $statusCode"
            }
            ResourceUsingDOAPIRequest.ServerError(
                ServerErrorContent(
                    status = "Error",
                    serverMessage = errorBody ?: errorMessage,
                    statusCode = statusCode
                )
            )
        }
        else -> ResourceUsingDOAPIRequest.Error(UnexpectedException(exception))
    }
}
