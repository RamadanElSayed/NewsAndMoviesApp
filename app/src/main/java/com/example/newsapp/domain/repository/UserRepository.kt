package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.Resource
import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.User
import com.example.newsapp.data.models.UserProfile
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

// Repository Interface
interface UserRepository {
    fun getUserById(id: Int): Flow<Resource<User>>
    fun createUser(user: User): Flow<Resource<User>>
    fun updateUser(id: Int, user: User): Flow<Resource<User>>
    fun deleteUser(id: Int): Flow<Resource<Unit>>
    fun getUsersByStatus(status: String): Flow<Resource<List<User>>>
    fun getUsersByFilters(filters: Map<String, String>): Flow<Resource<List<User>>>
    fun uploadImage(file: MultipartBody.Part, description: RequestBody): Flow<Resource<UploadResponse>>
    fun getUsersByUrl(url: String): Flow<Resource<List<User>>>
    fun createUserWithFields(name: String, job: String): Flow<Resource<User>>
    fun getUserProfile(token: String): Flow<Resource<UserProfile>>
    fun getUserProfileWithHeaders(headers: Map<String, String>): Flow<Resource<UserProfile>>
    fun registerUser(user: User): Flow<Resource<User>>
    fun doCreateUserWithField(name: String, job: String): Flow<Resource<User>>
}