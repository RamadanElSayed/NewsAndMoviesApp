package com.example.newsapp.data

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.retryWhen
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException

// Sealed class representing different resource states
sealed class ResourceUsingDOAPIRequest<out T> {
    data class Success<out T>(val data: T?) : ResourceUsingDOAPIRequest<T>()
    data class Error(val exception: Throwable) : ResourceUsingDOAPIRequest<Nothing>()
    data class ServerError(val errorContent: ServerErrorContent) :
        ResourceUsingDOAPIRequest<Nothing>()

    data object Loading : ResourceUsingDOAPIRequest<Nothing>()
}

// Class representing server error details
data class ServerErrorContent(
    val status: String,
    val serverMessage: String,
    val statusCode: Int,
    val errorCode: String? = null,
    val timestamp: String? = null
) : Exception("$statusCode: $serverMessage")

/**
 * Safely executes API calls with retries and handles exceptions.
 *
 * @param maxRetries Maximum number of retry attempts for transient errors.
 * @param apiCall The suspend function representing the API call.
 * @return A Flow emitting the Resource state (Loading, Success, Error, or ServerError).
 */
suspend fun <T> safeApiCall(
    maxRetries: Int = 3,
    apiCall: suspend () -> BaseResponse<T>
): Flow<ResourceUsingDOAPIRequest<T>> =
    flow {
        emit(ResourceUsingDOAPIRequest.Loading) // Emit loading state
        val response = apiCall()
        if (response.statusMsg.equals("fail", ignoreCase = true)) {
            throw ServerException(response.message)
        }
        emit(ResourceUsingDOAPIRequest.Success(response.data)) // Emit success state
    }
        .retryWhen { cause, attempt ->
            (cause is IOException || cause is TimeoutException) && attempt < maxRetries
        }
        .catch { exception ->
            emit(handleApiError(exception)) // Handle and emit errors appropriately
        }

/**
 * Maps exceptions to appropriate Resource error states.
 *
 * @param exception The exception thrown during the API call.
 * @return A Resource representing the error state (Error or ServerError).
 */
private fun handleApiError(exception: Throwable): ResourceUsingDOAPIRequest<Nothing> {
    // Log the exception for monitoring
    logError(exception)

    return when (exception) {
        is TimeoutException, is IOException -> ResourceUsingDOAPIRequest.Error(
            NetworkException(
                exception
            )
        )

        is HttpException -> {
            val statusCode = exception.code()
            val errorBody = exception.response()?.errorBody()?.string()
            val errorResponse = errorBody?.let { parseErrorResponse(it) }

            when (statusCode) {
                400 -> errorResponse.toServerError(statusCode, "Bad Request")
                401 -> errorResponse.toServerError(statusCode, "Unauthorized")
                403 -> errorResponse.toServerError(statusCode, "Forbidden")
                404 -> errorResponse.toServerError(statusCode, "Not Found")
                500 -> errorResponse.toServerError(statusCode, "Internal Server Error")
                else -> errorResponse.toServerError(
                    statusCode,
                    "Unexpected HTTP Error: $statusCode"
                )
            }
        }

        is JsonSyntaxException -> ResourceUsingDOAPIRequest.Error(ParsingException(exception))

        else -> ResourceUsingDOAPIRequest.Error(UnexpectedException(exception))
    }
}

/**
 * Parses an error response JSON string into a BaseResponse object with `Nothing` as the generic type.
 *
 * @param errorBody The JSON string from the error response.
 * @return The parsed BaseResponse object or null if parsing fails.
 */
private fun parseErrorResponse(errorBody: String): BaseResponse<Nothing>? {
    return try {
        @Suppress("UNCHECKED_CAST")
        Gson().fromJson(errorBody, BaseResponse::class.java) as BaseResponse<Nothing>
    } catch (e: JsonSyntaxException) {
        // Log the error or provide a fallback mechanism
        logError(JsonSyntaxException("Failed to parse error response: $errorBody"))
        null // Return null as a fallback
    }
}


/**
 * Extension function to convert a BaseResponse to a ServerError Resource.
 *
 * @param statusCode The HTTP status code.
 * @param defaultStatus The default status message if none is available.
 * @return A Resource.ServerError with the provided details.
 */
private fun BaseResponse<Nothing>?.toServerError(
    statusCode: Int,
    defaultStatus: String
): ResourceUsingDOAPIRequest.ServerError {
    return ResourceUsingDOAPIRequest.ServerError(
        ServerErrorContent(
            status = this?.statusMsg ?: defaultStatus,
            serverMessage = this?.message ?: "No additional information provided.",
            statusCode = statusCode,
            errorCode = this?.metadata?.errorCode,
            timestamp = this?.metadata?.timestamp
        )
    )
}

// General server-side exception
class ServerException(message: String?, cause: Throwable? = null) :
    Exception(message ?: "Server error occurred", cause)

// Network-related issues like timeouts or connectivity
class NetworkException(cause: Throwable, message: String? = null) :
    Exception(message ?: "Network error occurred", cause)

// Issues with parsing server responses
class ParsingException(cause: Throwable, message: String? = null) :
    Exception(message ?: "Parsing error occurred", cause)

// Unexpected exceptions not covered by specific types
class UnexpectedException(cause: Throwable, message: String? = null) :
    Exception(message ?: "Unexpected error occurred", cause)

// Example of a base response model with metadata
data class BaseResponse<T>(
    @SerializedName("statusMsg")
    val statusMsg: String? = null,
    @SerializedName("message")
    val message: String? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("metadata")
    val metadata: Metadata? = null
)

// Metadata class for additional response details
data class Metadata(
    @SerializedName("timestamp")
    val timestamp: String? = null,
    @SerializedName("requestId")
    val requestId: String? = null,
    @SerializedName("errorCode")
    val errorCode: String? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null
)

// Pagination details
data class Pagination(
    @SerializedName("currentPage")
    val currentPage: Int,
    @SerializedName("totalPages")
    val totalPages: Int,
    @SerializedName("totalResults")
    val totalResults: Int
)

/**
 * Logs the error for monitoring purposes.
 */
private fun logError(exception: Throwable) {
    // Replace this with an actual logging/analytics tool
    println("Error occurred: ${exception.message}")
}
