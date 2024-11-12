package com.example.newsapp.presentabon.moviescreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.*
import com.example.newsapp.domain.repository.TMDBRepository
import com.example.newsapp.presentabon.moviescreen.uimodel.TMDBIntent
import com.example.newsapp.presentabon.moviescreen.uimodel.TMDBState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class TMDBViewModel @Inject constructor(
    private val repository: TMDBRepository
) : ViewModel() {

    // Intent Channel
    val intentChannel = Channel<TMDBIntent>(Channel.UNLIMITED)

    // State
    private val _state = MutableStateFlow(TMDBState())
    val state: StateFlow<TMDBState> = _state.asStateFlow()

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is TMDBIntent.InitializeSession -> initializeSession(intent.apiKey)

                    is TMDBIntent.LoadPopularMovies -> loadPopularMovies(intent.apiKey, intent.language, intent.page)
                    is TMDBIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId, intent.apiKey)
                    is TMDBIntent.SearchMovies -> searchMovies(intent.query, intent.apiKey, intent.language, intent.page)
                    is TMDBIntent.RateMovie -> rateMovie(intent.movieId, intent.apiKey, intent.sessionId, intent.rating)
                    is TMDBIntent.AddMovieToWatchlist -> addMovieToWatchlist(intent.accountId, intent.apiKey, intent.sessionId, intent.movieId)

                    is TMDBIntent.LoadPopularTVShows -> loadPopularTVShows(intent.apiKey, intent.language, intent.page)
                    is TMDBIntent.LoadTVDetails -> loadTVDetails(intent.tvId, intent.apiKey)
                    is TMDBIntent.RateTVShow -> rateTVShow(intent.tvId, intent.apiKey, intent.sessionId, intent.rating)
                    is TMDBIntent.AddTVToWatchlist -> addTVToWatchlist(intent.accountId, intent.apiKey, intent.sessionId, intent.tvId)

                    is TMDBIntent.LoadAccountDetails -> loadAccountDetails(intent.apiKey, intent.sessionId)
                    is TMDBIntent.LoadFavoriteMovies -> loadFavoriteMovies(intent.accountId, intent.apiKey, intent.sessionId)
                    is TMDBIntent.LoadWatchlistMovies -> loadWatchlistMovies(intent.accountId, intent.apiKey, intent.sessionId)

                    is TMDBIntent.AddFavorite -> addFavorite(intent.accountId, intent.apiKey, intent.sessionId, intent.mediaId, intent.mediaType, intent.favorite)


                }
            }
        }
    }

    private fun initializeSession(apiKey: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            // Step 1: Request Token
            val requestTokenResponse = repository.createRequestToken(apiKey).first()
            if (requestTokenResponse is Resource.Success) {
                val requestToken = requestTokenResponse.data.requestToken

                // Step 2: Direct the user to authenticate the request token

                // Step 3: Create Session after authentication
                val sessionResponse = repository.createSession(apiKey, RequestToken(requestToken)).first()
                if (sessionResponse is Resource.Success && sessionResponse.data.sessionId != null) {
                    val sessionId = sessionResponse.data.sessionId
                    _state.update { it.copy(sessionId = sessionId) }

                    // Step 4: Retrieve the Account ID
                    val accountDetailsResponse = repository.getAccountDetails(apiKey, sessionId).first()
                    if (accountDetailsResponse is Resource.Success) {
                        _state.update { currentState ->
                            currentState.copy(
                                accountId = accountDetailsResponse.data.id.toString(),
                                isLoading = false
                            )
                        }
                    } else if (accountDetailsResponse is Resource.Error) {
                        _state.update { it.copy(isLoading = false, errorMessage = accountDetailsResponse.message) }
                    }
                } else if (sessionResponse is Resource.Error) {
                    _state.update { it.copy(isLoading = false, errorMessage = sessionResponse.message) }
                }
            } else if (requestTokenResponse is Resource.Error) {
                _state.update { it.copy(isLoading = false, errorMessage = requestTokenResponse.message) }
            }
        }
    }

    // Movie functions
    private fun loadPopularMovies(apiKey: String, language: String, page: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getPopularMovies(apiKey, language, page).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, popularMovies = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun loadMovieDetails(movieId: Int, apiKey: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getMovieDetails(movieId, apiKey).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, movieDetail = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun rateMovie(movieId: Int, apiKey: String, sessionId: String, rating: Double) = viewModelScope.launch {
        repository.rateMovie(movieId, apiKey, sessionId, RatingRequest(rating)).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Loading -> it.copy(isLoading = true)
                    is Resource.Success -> it.copy(isLoading = false, movieRatingStatus = resource.data)
                    is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun addMovieToWatchlist(accountId: String, apiKey: String, sessionId: String, movieId: Int) = viewModelScope.launch {
        repository.addToWatchlist(accountId, apiKey, sessionId, WatchlistRequest("movie", movieId, true)).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Loading -> it.copy(isLoading = true)
                    is Resource.Success -> it.copy(isLoading = false, movieWatchlistStatus = resource.data)
                    is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    // TV functions
    private fun loadPopularTVShows(apiKey: String, language: String, page: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getPopularTVShows(apiKey, language, page).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, popularTVShows = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun loadTVDetails(tvId: Int, apiKey: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getTVDetails(tvId, apiKey).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, tvDetail = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun rateTVShow(tvId: Int, apiKey: String, sessionId: String, rating: Double) = viewModelScope.launch {
        repository.rateTVShow(tvId, apiKey, sessionId, RatingRequest(rating)).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Loading -> it.copy(isLoading = true)
                    is Resource.Success -> it.copy(isLoading = false, tvRatingStatus = resource.data)
                    is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun addTVToWatchlist(accountId: String, apiKey: String, sessionId: String, tvId: Int) = viewModelScope.launch {
        repository.addToWatchlist(accountId, apiKey, sessionId, WatchlistRequest("tv", tvId, true)).collect { resource ->
            _state.update {
                when (resource) {
                    is Resource.Loading -> it.copy(isLoading = true)
                    is Resource.Success -> it.copy(isLoading = false, tvWatchlistStatus = resource.data)
                    is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    // Account functions
    private fun loadAccountDetails(apiKey: String, sessionId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getAccountDetails(apiKey, sessionId).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, accountDetails = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun loadFavoriteMovies(accountId: String, apiKey: String, sessionId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getFavoriteMovies(accountId, apiKey, sessionId).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, favoriteMovies = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun loadWatchlistMovies(accountId: String, apiKey: String, sessionId: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.getWatchlistMovies(accountId, apiKey, sessionId).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, watchlistMovies = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun searchMovies(query: String, apiKey: String, language: String, page: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true, errorMessage = null) }
        repository.searchMovies(apiKey, query, language, page).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is Resource.Loading -> currentState.copy(isLoading = true)
                    is Resource.Success -> currentState.copy(isLoading = false, searchedMovies = resource.data)
                    is Resource.Error -> currentState.copy(isLoading = false, errorMessage = resource.message)
                }
            }
        }
    }

    private fun addFavorite(accountId: String, apiKey: String, sessionId: String, mediaId: Int, mediaType: String, favorite: Boolean) {
        viewModelScope.launch {
            repository.addFavorite(accountId, apiKey, sessionId, FavoriteRequest(mediaType, mediaId, favorite)).collect { resource ->
                _state.update {
                    when (resource) {
                        is Resource.Loading -> it.copy(isLoading = true)
                        is Resource.Success -> it.copy(isLoading = false, favoriteStatus = resource.data)
                        is Resource.Error -> it.copy(isLoading = false, errorMessage = resource.message)
                    }
                }
            }
        }
    }


    fun uploadImage(file: File) {
        val filePart = createFilePart(file)
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            repository.uploadImage(filePart).collect { result ->
                _state.update { currentState ->
                    when (result) {
                        is Resource.Loading -> currentState.copy(isLoading = true)
                        is Resource.Success -> currentState.copy(
                            isLoading = false,
                            uploadState = result.data // Directly update uploadState with UploadResponse
                        )
                        is Resource.Error -> currentState.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            uploadState = null // Clear uploadState on error
                        )
                    }
                }
            }
        }
    }


    private fun createFilePart(file: File): MultipartBody.Part {
        // Create RequestBody from file using asRequestBody extension
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())

        // Create the multipart part with form data
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
}