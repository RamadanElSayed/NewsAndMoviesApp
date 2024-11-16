package com.example.newsapp.domain.repository

import com.example.newsapp.data.ResourceUsingDOAPIRequest
import com.example.newsapp.data.models.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TMDRepositoryUsingDOAPIRequest {

    // Account
    suspend fun getAccountDetails(apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<AccountDetails>>
    suspend fun addFavorite(accountId: String, apiKey: String, sessionId: String, favoriteRequest: FavoriteRequest): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>
    suspend fun addToWatchlist(accountId: String, apiKey: String, sessionId: String, watchlistRequest: WatchlistRequest): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>
    suspend  fun getFavoriteMovies(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend fun getFavoriteTVShows(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<TVListResponse>>
    suspend  fun getRatedMovies(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend fun getRatedTVShows(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<TVListResponse>>
    suspend fun getWatchlistMovies(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend fun getWatchlistTVShows(accountId: String, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<TVListResponse>>

    // Authentication
    suspend fun createGuestSession(apiKey: String): Flow<ResourceUsingDOAPIRequest<SessionResponse>>
    suspend fun createRequestToken(apiKey: String): Flow<ResourceUsingDOAPIRequest<RequestTokenResponse>>
    suspend  fun createSession(apiKey: String, requestToken: RequestToken): Flow<ResourceUsingDOAPIRequest<SessionResponse>>
    suspend fun deleteSession(apiKey: String, session: Session): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>

    // Certifications
    suspend fun getMovieCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>>
    suspend fun getTVCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>>

    // Movies
    suspend  fun getPopularMovies(apiKey: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend  fun getMovieDetails(movieId: Int, apiKey: String): Flow<ResourceUsingDOAPIRequest<MovieDetailResponse>>
    suspend  fun rateMovie(movieId: Int, apiKey: String, sessionId: String, ratingRequest: RatingRequest): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>
    suspend  fun deleteMovieRating(movieId: Int, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>

    // TV
    suspend  fun getPopularTVShows(apiKey: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>>
    suspend  fun getTVDetails(tvId: Int, apiKey: String): Flow<ResourceUsingDOAPIRequest<TVDetailResponse>>
    suspend  fun rateTVShow(tvId: Int, apiKey: String, sessionId: String, ratingRequest: RatingRequest): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>
    suspend   fun deleteTVRating(tvId: Int, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<ResponseStatus>>

    // Discover
    suspend  fun discoverMovies(apiKey: String, language: String, sortBy: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend fun discoverTVShows(apiKey: String, language: String, sortBy: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>>

    // Search
    suspend fun searchMovies(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>>
    suspend fun searchTVShows(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>>
    suspend fun searchPeople(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<PeopleListResponse>>

    suspend fun uploadImage(file: MultipartBody.Part): Flow<ResourceUsingDOAPIRequest<UploadResponse>>
}
