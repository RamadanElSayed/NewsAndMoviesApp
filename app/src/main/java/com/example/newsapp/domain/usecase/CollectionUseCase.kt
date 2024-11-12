package com.example.newsapp.domain.usecase

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import com.example.newsapp.data.models.*
import com.example.newsapp.domain.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class GetUserByIdUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(id: Int): Flow<Resource<User>> = repository.getUserById(id)
}

class CreateUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(user: User): Flow<Resource<User>> = repository.createUser(user)
}

class RegisterUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(user: User): Flow<Resource<User>> = repository.registerUser(user)
}

class UpdateUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(id: Int, user: User): Flow<Resource<User>> = repository.updateUser(id, user)
}

class DeleteUserUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(id: Int): Flow<Resource<Unit>> = repository.deleteUser(id)
}

class UploadImageUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(file: MultipartBody.Part, description: RequestBody): Flow<Resource<UploadResponse>> =
        repository.uploadImage(file, description)
}

class GetUsersByStatusUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(status: String): Flow<Resource<List<User>>> = repository.getUsersByStatus(status)
}

class GetUsersByFiltersUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(filters: Map<String, String>): Flow<Resource<List<User>>> = repository.getUsersByFilters(filters)
}

class GetUserProfileUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(token: String): Flow<Resource<UserProfile>> = repository.getUserProfile(token)
}

class GetUserProfileWithHeadersUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(headers: Map<String, String>): Flow<Resource<UserProfile>> = repository.getUserProfileWithHeaders(headers)
}

class GetUsersByUrlUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(url: String): Flow<Resource<List<User>>> = repository.getUsersByUrl(url)
}

class DoCreateUserWithFieldUseCase @Inject constructor(private val repository: UserRepository) {
    operator fun invoke(name: String, job: String): Flow<Resource<User>> = repository.doCreateUserWithField(name, job)
}
