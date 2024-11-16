package com.example.newsapp.presentabon.moviescreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.models.*
import com.example.newsapp.domain.usecase.TMDBUseCases
import com.example.newsapp.data.ResourceUsingDOAPIRequest
import com.example.newsapp.presentabon.moviescreen.uimodel.TMDBIntent
import com.example.newsapp.presentabon.moviescreen.uimodel.TMDBState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.File
import javax.inject.Inject

class TMDBViewModelUsingDoApiRequest @Inject constructor(
    private val useCases: TMDBUseCases
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
                    is TMDBIntent.LoadPopularMovies -> loadPopularMovies(intent.apiKey, intent.language, intent.page)
                    is TMDBIntent.LoadMovieDetails -> loadMovieDetails(intent.movieId, intent.apiKey)
                    is TMDBIntent.SearchMovies -> searchMovies(intent.query, intent.apiKey, intent.language, intent.page)
                    is TMDBIntent.RateMovie -> rateMovie(intent.movieId, intent.apiKey, intent.sessionId, intent.rating)
                    is TMDBIntent.AddMovieToWatchlist -> addMovieToWatchlist(intent.accountId, intent.apiKey, intent.sessionId, intent.movieId)
                    is TMDBIntent.AddFavorite -> TODO()
                    is TMDBIntent.AddTVToWatchlist -> TODO()
                    is TMDBIntent.InitializeSession -> TODO()
                    is TMDBIntent.LoadAccountDetails -> TODO()
                    is TMDBIntent.LoadFavoriteMovies -> TODO()
                    is TMDBIntent.LoadPopularTVShows -> TODO()
                    is TMDBIntent.LoadTVDetails -> TODO()
                    is TMDBIntent.LoadWatchlistMovies -> TODO()
                    is TMDBIntent.RateTVShow -> TODO()
                }
            }
        }
    }

    // Example: Load Popular Movies
    private fun loadPopularMovies(apiKey: String, language: String, page: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCases.getPopularMovies(apiKey, language, page).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is ResourceUsingDOAPIRequest.Loading -> currentState.copy(isLoading = true)
                    is ResourceUsingDOAPIRequest.Success -> currentState.copy(isLoading = false, popularMovies = resource.data)
                    is ResourceUsingDOAPIRequest.Error -> currentState.copy(isLoading = false, errorMessage = resource.exception.message)
                    is ResourceUsingDOAPIRequest.ServerError -> currentState.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                }
            }
        }
    }

    // Example: Load Movie Details
    private fun loadMovieDetails(movieId: Int, apiKey: String) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCases.getMovieDetails(movieId, apiKey).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is ResourceUsingDOAPIRequest.Loading -> currentState.copy(isLoading = true)
                    is ResourceUsingDOAPIRequest.Success -> currentState.copy(isLoading = false, movieDetail = resource.data)
                    is ResourceUsingDOAPIRequest.Error -> currentState.copy(isLoading = false, errorMessage = resource.exception.message)
                    is ResourceUsingDOAPIRequest.ServerError -> currentState.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                }
            }
        }
    }

    // Example: Search Movies
    private fun searchMovies(query: String, apiKey: String, language: String, page: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        useCases.searchMovies(apiKey, query, language, page).collect { resource ->
            _state.update { currentState ->
                when (resource) {
                    is ResourceUsingDOAPIRequest.Loading -> currentState.copy(isLoading = true)
                    is ResourceUsingDOAPIRequest.Success -> currentState.copy(isLoading = false, searchedMovies = resource.data)
                    is ResourceUsingDOAPIRequest.Error -> currentState.copy(isLoading = false, errorMessage = resource.exception.message)
                    is ResourceUsingDOAPIRequest.ServerError -> currentState.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                }
            }
        }
    }

    // Example: Rate Movie
    private fun rateMovie(movieId: Int, apiKey: String, sessionId: String, rating: Double) = viewModelScope.launch {
        useCases.rateMovie(movieId, apiKey, sessionId, RatingRequest(rating)).collect { resource ->
            _state.update {
                when (resource) {
                    is ResourceUsingDOAPIRequest.Loading -> it.copy(isLoading = true)
                    is ResourceUsingDOAPIRequest.Success -> it.copy(isLoading = false, movieRatingStatus = resource.data)
                    is ResourceUsingDOAPIRequest.Error -> it.copy(isLoading = false, errorMessage = resource.exception.message)
                    is ResourceUsingDOAPIRequest.ServerError -> it.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                }
            }
        }
    }

    // Example: Add Movie to Watchlist
    private fun addMovieToWatchlist(accountId: String, apiKey: String, sessionId: String, movieId: Int) = viewModelScope.launch {
        useCases.addToWatchlist(accountId, apiKey, sessionId, WatchlistRequest("movie", movieId, true)).collect { resource ->
            _state.update {
                when (resource) {
                    is ResourceUsingDOAPIRequest.Loading -> it.copy(isLoading = true)
                    is ResourceUsingDOAPIRequest.Success -> it.copy(isLoading = false, movieWatchlistStatus = resource.data)
                    is ResourceUsingDOAPIRequest.Error -> it.copy(isLoading = false, errorMessage = resource.exception.message)
                    is ResourceUsingDOAPIRequest.ServerError -> it.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                }
            }
        }
    }

    // Example: Upload Image
    private fun uploadImage(file: File) {
        val filePart = createFilePart(file)
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            useCases.uploadImage(filePart).collect { resource ->
                _state.update {
                    when (resource) {
                        is ResourceUsingDOAPIRequest.Loading -> it.copy(isLoading = true)
                        is ResourceUsingDOAPIRequest.Success -> it.copy(isLoading = false, uploadState = resource.data)
                        is ResourceUsingDOAPIRequest.Error -> it.copy(isLoading = false, errorMessage = resource.exception.message)
                        is ResourceUsingDOAPIRequest.ServerError -> it.copy(isLoading = false, errorMessage = resource.errorContent.serverMessage)
                    }
                }
            }
        }
    }

    private fun createFilePart(file: File): MultipartBody.Part {
        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }
}
