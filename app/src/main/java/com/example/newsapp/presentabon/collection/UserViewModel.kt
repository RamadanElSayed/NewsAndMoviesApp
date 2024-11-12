package com.example.newsapp.presentabon.collection
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.*
import com.example.newsapp.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

sealed class UserIntent {
    data class GetUserById(val id: Int) : UserIntent()
    data class CreateUser(val user: User) : UserIntent()
    data class RegisterUser(val user: User) : UserIntent()
    data class UpdateUser(val id: Int, val user: User) : UserIntent()
    data class DeleteUser(val id: Int) : UserIntent()
    data class UploadImage(val file: MultipartBody.Part, val description: RequestBody) : UserIntent()
    data class GetUsersByStatus(val status: String) : UserIntent()
    data class GetUsersByFilters(val filters: Map<String, String>) : UserIntent()
    data class GetUserProfile(val token: String) : UserIntent()
    data class GetUserProfileWithHeaders(val headers: Map<String, String>) : UserIntent()
    data class GetUsersByUrl(val url: String) : UserIntent()
    data class DoCreateUserWithField(val name: String, val job: String) : UserIntent()
}

data class UserViewState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val users: List<User>? = null,
    val userProfile: UserProfile? = null,
    val uploadResponse: UploadResponse? = null,
    val errorMessage: String? = null
)

@HiltViewModel
class UserViewModelCollection @Inject constructor(
    private val getUserByIdUseCase: GetUserByIdUseCase,
    private val createUserUseCase: CreateUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val uploadImageUseCase: UploadImageUseCase,
    private val getUsersByStatusUseCase: GetUsersByStatusUseCase,
    private val getUsersByFiltersUseCase: GetUsersByFiltersUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserProfileWithHeadersUseCase: GetUserProfileWithHeadersUseCase,
    private val getUsersByUrlUseCase: GetUsersByUrlUseCase,
    private val doCreateUserWithFieldUseCase: DoCreateUserWithFieldUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UserViewState())
    val state: StateFlow<UserViewState> = _state

    private val _intentChannel = Channel<UserIntent>(Channel.UNLIMITED)
    val intentChannel = _intentChannel

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            _intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is UserIntent.GetUserById -> getUserById(intent.id)
                    is UserIntent.CreateUser -> createUser(intent.user)
                    is UserIntent.RegisterUser -> registerUser(intent.user)
                    is UserIntent.UpdateUser -> updateUser(intent.id, intent.user)
                    is UserIntent.DeleteUser -> deleteUser(intent.id)
                    is UserIntent.UploadImage -> uploadImage(intent.file, intent.description)
                    is UserIntent.GetUsersByStatus -> getUsersByStatus(intent.status)
                    is UserIntent.GetUsersByFilters -> getUsersByFilters(intent.filters)
                    is UserIntent.GetUserProfile -> getUserProfile(intent.token)
                    is UserIntent.GetUserProfileWithHeaders -> getUserProfileWithHeaders(intent.headers)
                    is UserIntent.GetUsersByUrl -> getUsersByUrl(intent.url)
                    is UserIntent.DoCreateUserWithField -> doCreateUserWithField(intent.name, intent.job)
                }
            }
        }
    }

    private fun getUserById(id: Int) = executeUseCase {
        getUserByIdUseCase(id)
    }

    private fun createUser(user: User) = executeUseCase {
        createUserUseCase(user)
    }

    private fun registerUser(user: User) = executeUseCase {
        registerUserUseCase(user)
    }

    private fun updateUser(id: Int, user: User) = executeUseCase {
        updateUserUseCase(id, user)
    }

    private fun deleteUser(id: Int) = executeUseCase {
        deleteUserUseCase(id)
    }

    private fun uploadImage(file: MultipartBody.Part, description: RequestBody) = executeUseCase {
        uploadImageUseCase(file, description)
    }

    private fun getUsersByStatus(status: String) = executeUseCase {
        getUsersByStatusUseCase(status)
    }

    private fun getUsersByFilters(filters: Map<String, String>) = executeUseCase {
        getUsersByFiltersUseCase(filters)
    }

    private fun getUserProfile(token: String) = executeUseCase {
        getUserProfileUseCase(token)
    }

    private fun getUserProfileWithHeaders(headers: Map<String, String>) = executeUseCase {
        getUserProfileWithHeadersUseCase(headers)
    }

    private fun getUsersByUrl(url: String) = executeUseCase {
        getUsersByUrlUseCase(url)
    }

    private fun doCreateUserWithField(name: String, job: String) = executeUseCase {
        doCreateUserWithFieldUseCase(name, job)
    }

    private fun <T> executeUseCase(flow: () -> Flow<Resource<T>>) {
        viewModelScope.launch {
            flow().collect { result ->
                when (result) {
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> handleSuccess(result.data)
                    is Resource.Error -> _state.update { it.copy(isLoading = false, errorMessage = result.message) }
                }
            }
        }
    }

    private fun <T> handleSuccess(data: T?) {
        _state.update { currentState ->
            when (data) {
                is User -> currentState.copy(isLoading = false, user = data, errorMessage = null)
                is List<*> -> currentState.copy(isLoading = false, users = data.filterIsInstance<User>(), errorMessage = null)
                is UserProfile -> currentState.copy(isLoading = false, userProfile = data, errorMessage = null)
                is UploadResponse -> currentState.copy(isLoading = false, uploadResponse = data, errorMessage = null)
                else -> currentState.copy(isLoading = false, errorMessage = "Unknown data type")
            }
        }
    }
}
