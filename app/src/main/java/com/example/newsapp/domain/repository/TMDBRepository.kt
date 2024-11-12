package com.example.newsapp.domain.repository

import com.example.newsapp.data.models.*
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody

interface TMDBRepository {

    // Account
    fun getAccountDetails(apiKey: String, sessionId: String): Flow<Resource<AccountDetails>>
    fun addFavorite(accountId: String, apiKey: String, sessionId: String, favoriteRequest: FavoriteRequest): Flow<Resource<ResponseStatus>>
    fun addToWatchlist(accountId: String, apiKey: String, sessionId: String, watchlistRequest: WatchlistRequest): Flow<Resource<ResponseStatus>>
    fun getFavoriteMovies(accountId: String, apiKey: String, sessionId: String): Flow<Resource<MovieListResponse>>
    fun getFavoriteTVShows(accountId: String, apiKey: String, sessionId: String): Flow<Resource<TVListResponse>>
    fun getRatedMovies(accountId: String, apiKey: String, sessionId: String): Flow<Resource<MovieListResponse>>
    fun getRatedTVShows(accountId: String, apiKey: String, sessionId: String): Flow<Resource<TVListResponse>>
    fun getWatchlistMovies(accountId: String, apiKey: String, sessionId: String): Flow<Resource<MovieListResponse>>
    fun getWatchlistTVShows(accountId: String, apiKey: String, sessionId: String): Flow<Resource<TVListResponse>>

    // Authentication
    fun createGuestSession(apiKey: String): Flow<Resource<SessionResponse>>
    fun createRequestToken(apiKey: String): Flow<Resource<RequestTokenResponse>>
    fun createSession(apiKey: String, requestToken: RequestToken): Flow<Resource<SessionResponse>>
    fun deleteSession(apiKey: String, session: Session): Flow<Resource<ResponseStatus>>

    // Certifications
    fun getMovieCertifications(apiKey: String): Flow<Resource<CertificationResponse>>
    fun getTVCertifications(apiKey: String): Flow<Resource<CertificationResponse>>

    // Movies
    fun getPopularMovies(apiKey: String, language: String, page: Int): Flow<Resource<MovieListResponse>>
    fun getMovieDetails(movieId: Int, apiKey: String): Flow<Resource<MovieDetailResponse>>
    fun rateMovie(movieId: Int, apiKey: String, sessionId: String, ratingRequest: RatingRequest): Flow<Resource<ResponseStatus>>
    fun deleteMovieRating(movieId: Int, apiKey: String, sessionId: String): Flow<Resource<ResponseStatus>>

    // TV
    fun getPopularTVShows(apiKey: String, language: String, page: Int): Flow<Resource<TVListResponse>>
    fun getTVDetails(tvId: Int, apiKey: String): Flow<Resource<TVDetailResponse>>
    fun rateTVShow(tvId: Int, apiKey: String, sessionId: String, ratingRequest: RatingRequest): Flow<Resource<ResponseStatus>>
    fun deleteTVRating(tvId: Int, apiKey: String, sessionId: String): Flow<Resource<ResponseStatus>>

    // Discover
    fun discoverMovies(apiKey: String, language: String, sortBy: String, page: Int): Flow<Resource<MovieListResponse>>
    fun discoverTVShows(apiKey: String, language: String, sortBy: String, page: Int): Flow<Resource<TVListResponse>>

    // Search
    fun searchMovies(apiKey: String, query: String, language: String, page: Int): Flow<Resource<MovieListResponse>>
    fun searchTVShows(apiKey: String, query: String, language: String, page: Int): Flow<Resource<TVListResponse>>
    fun searchPeople(apiKey: String, query: String, language: String, page: Int): Flow<Resource<PeopleListResponse>>

    fun uploadImage(file: MultipartBody.Part): Flow<Resource<UploadResponse>>
}
