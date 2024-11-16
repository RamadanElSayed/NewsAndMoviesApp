package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.User
import com.example.newsapp.data.models.UserProfile
import com.example.newsapp.data.repestory.ResourceUsingDOAPIRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody

// Repository Interface
// Repository interface
interface UserRepository {
    suspend fun getUserById(id: Int): Flow<ResourceUsingDOAPIRequest<User>>
    suspend fun createUser(user: User): Flow<ResourceUsingDOAPIRequest<User>>
    suspend fun updateUser(id: Int, user: User): Flow<ResourceUsingDOAPIRequest<User>>
    suspend fun deleteUser(id: Int): Flow<ResourceUsingDOAPIRequest<Unit>>
    suspend fun getUsersByStatus(status: String): Flow<ResourceUsingDOAPIRequest<List<User>>>
    suspend fun getUsersByFilters(filters: Map<String, String>): Flow<ResourceUsingDOAPIRequest<List<User>>>
    suspend fun uploadImage(file: MultipartBody.Part, description: RequestBody): Flow<ResourceUsingDOAPIRequest<UploadResponse>>
    suspend fun getUsersByUrl(url: String): Flow<ResourceUsingDOAPIRequest<List<User>>>
    suspend fun createUserWithFields(name: String, job: String): Flow<ResourceUsingDOAPIRequest<User>>
    suspend fun getUserProfile(token: String): Flow<ResourceUsingDOAPIRequest<UserProfile>>
    suspend  fun getUserProfileWithHeaders(headers: Map<String, String>): Flow<ResourceUsingDOAPIRequest<UserProfile>>
    suspend fun registerUser(user: User): Flow<ResourceUsingDOAPIRequest<User>>
    suspend fun doCreateUserWithField(name: String, job: String): Flow<ResourceUsingDOAPIRequest<User>>
}
