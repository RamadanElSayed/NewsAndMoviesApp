package com.example.newsapp.data.repestory

import com.example.newsapp.data.models.Resource
import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.User
import com.example.newsapp.data.models.UserProfile
import com.example.newsapp.data.remote.ApiService
import com.example.newsapp.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import javax.inject.Inject

// Repository Implementation
class UserRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : UserRepository {

    override fun getUserById(id: Int): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getUserById(id)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun createUser(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.createUser(user)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun updateUser(id: Int, user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.updateUser(id, user)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun deleteUser(id: Int): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.deleteUser(id)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun getUsersByStatus(status: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getUsersByStatus(status)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun getUsersByFilters(filters: Map<String, String>): Flow<Resource<List<User>>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getUsersByFilters(filters)
                emit(handleResponse(response))
            } catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }

    override fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody
    ): Flow<Resource<UploadResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.uploadImage(file, description)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun getUsersByUrl(url: String): Flow<Resource<List<User>>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getUsersByUrl(url)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun createUserWithFields(name: String, job: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.doCreateUserWithField(name, job)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun getUserProfile(token: String): Flow<Resource<UserProfile>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.getUserProfile(token)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    override fun getUserProfileWithHeaders(headers: Map<String, String>): Flow<Resource<UserProfile>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = apiService.getUserProfileWithHeaders(headers)
                emit(handleResponse(response))
            } catch (e: Exception) {
                emit(Resource.Error("Exception: ${e.message}"))
            }
        }


    override fun registerUser(user: User): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.registerUser(user)
            emit(handleResponse(response))
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }

    private fun <T> handleResponse(response: Response<T>): Resource<T> {
        return if (response.isSuccessful) {
            response.body()?.let { Resource.Success(it) } ?: Resource.Error("Empty response body")
        } else {
            Resource.Error("Error: ${response.code()}")
        }
    }
    override fun doCreateUserWithField(name: String, job: String): Flow<Resource<User>> = flow {
        emit(Resource.Loading())
        try {
            val response = apiService.doCreateUserWithField(name, job)
            if (response.isSuccessful) {
                response.body()?.let { user ->
                    emit(Resource.Success(user))
                } ?: emit(Resource.Error("Empty response body"))
            } else {
                emit(Resource.Error("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Exception: ${e.message}"))
        }
    }


}