package com.example.newsapp.domain.usecase

import com.example.newsapp.data.ResourceUsingDOAPIRequest
import com.example.newsapp.data.models.*
import com.example.newsapp.domain.repository.TMDRepositoryUsingDOAPIRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class TMDBUseCases @Inject constructor(private val repository: TMDRepositoryUsingDOAPIRequest) {

    // ------------------- ACCOUNT -------------------

    suspend fun getAccountDetails(apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<AccountDetails>> =
        repository.getAccountDetails(apiKey, sessionId)

    suspend fun addFavorite(
        accountId: String,
        apiKey: String,
        sessionId: String,
        favoriteRequest: FavoriteRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.addFavorite(accountId, apiKey, sessionId, favoriteRequest)

    suspend fun addToWatchlist(
        accountId: String,
        apiKey: String,
        sessionId: String,
        watchlistRequest: WatchlistRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.addToWatchlist(accountId, apiKey, sessionId, watchlistRequest)

    suspend fun getFavoriteMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.getFavoriteMovies(accountId, apiKey, sessionId)

    suspend fun getFavoriteTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.getFavoriteTVShows(accountId, apiKey, sessionId)

    suspend fun getRatedMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.getRatedMovies(accountId, apiKey, sessionId)

    suspend fun getRatedTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.getRatedTVShows(accountId, apiKey, sessionId)

    suspend fun getWatchlistMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.getWatchlistMovies(accountId, apiKey, sessionId)

    suspend fun getWatchlistTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.getWatchlistTVShows(accountId, apiKey, sessionId)

    // ------------------- AUTHENTICATION -------------------

    suspend fun createGuestSession(apiKey: String): Flow<ResourceUsingDOAPIRequest<SessionResponse>> =
        repository.createGuestSession(apiKey)

    suspend fun createRequestToken(apiKey: String): Flow<ResourceUsingDOAPIRequest<RequestTokenResponse>> =
        repository.createRequestToken(apiKey)

    suspend fun createSession(
        apiKey: String,
        requestToken: RequestToken
    ): Flow<ResourceUsingDOAPIRequest<SessionResponse>> =
        repository.createSession(apiKey, requestToken)

    suspend fun deleteSession(apiKey: String, session: Session): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.deleteSession(apiKey, session)

    // ------------------- CERTIFICATIONS -------------------

    suspend fun getMovieCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>> =
        repository.getMovieCertifications(apiKey)

    suspend fun getTVCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>> =
        repository.getTVCertifications(apiKey)

    // ------------------- MOVIES -------------------

    suspend fun getPopularMovies(apiKey: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.getPopularMovies(apiKey, language, page)

    suspend fun getMovieDetails(movieId: Int, apiKey: String): Flow<ResourceUsingDOAPIRequest<MovieDetailResponse>> =
        repository.getMovieDetails(movieId, apiKey)

    suspend fun rateMovie(
        movieId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.rateMovie(movieId, apiKey, sessionId, ratingRequest)

    suspend fun deleteMovieRating(movieId: Int, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.deleteMovieRating(movieId, apiKey, sessionId)

    // ------------------- TV -------------------

    suspend fun getPopularTVShows(apiKey: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.getPopularTVShows(apiKey, language, page)

    suspend fun getTVDetails(tvId: Int, apiKey: String): Flow<ResourceUsingDOAPIRequest<TVDetailResponse>> =
        repository.getTVDetails(tvId, apiKey)

    suspend fun rateTVShow(
        tvId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.rateTVShow(tvId, apiKey, sessionId, ratingRequest)

    suspend fun deleteTVRating(tvId: Int, apiKey: String, sessionId: String): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        repository.deleteTVRating(tvId, apiKey, sessionId)

    // ------------------- DISCOVER -------------------

    suspend fun discoverMovies(apiKey: String, language: String, sortBy: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.discoverMovies(apiKey, language, sortBy, page)

    suspend fun discoverTVShows(apiKey: String, language: String, sortBy: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.discoverTVShows(apiKey, language, sortBy, page)

    // ------------------- SEARCH -------------------

    suspend fun searchMovies(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        repository.searchMovies(apiKey, query, language, page)

    suspend fun searchTVShows(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        repository.searchTVShows(apiKey, query, language, page)

    suspend fun searchPeople(apiKey: String, query: String, language: String, page: Int): Flow<ResourceUsingDOAPIRequest<PeopleListResponse>> =
        repository.searchPeople(apiKey, query, language, page)

    // ------------------- UPLOAD -------------------

    suspend fun uploadImage(file: MultipartBody.Part): Flow<ResourceUsingDOAPIRequest<UploadResponse>> =
        repository.uploadImage(file)
}
