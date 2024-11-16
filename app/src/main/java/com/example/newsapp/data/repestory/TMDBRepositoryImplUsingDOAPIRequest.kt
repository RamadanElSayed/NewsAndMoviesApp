package com.example.newsapp.data.repestory

import com.example.newsapp.data.ResourceUsingDOAPIRequest
import com.example.newsapp.data.models.AccountDetails
import com.example.newsapp.data.models.CertificationResponse
import com.example.newsapp.data.models.FavoriteRequest
import com.example.newsapp.data.models.MovieDetailResponse
import com.example.newsapp.data.models.MovieListResponse
import com.example.newsapp.data.models.PeopleListResponse
import com.example.newsapp.data.models.RatingRequest
import com.example.newsapp.data.models.RequestToken
import com.example.newsapp.data.models.RequestTokenResponse
import com.example.newsapp.data.models.ResponseStatus
import com.example.newsapp.data.models.Session
import com.example.newsapp.data.models.SessionResponse
import com.example.newsapp.data.models.TVDetailResponse
import com.example.newsapp.data.models.TVListResponse
import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.WatchlistRequest
import com.example.newsapp.data.remote.TMDServiceUsingBaseResponse
import com.example.newsapp.data.safeApiCall
import com.example.newsapp.domain.repository.TMDRepositoryUsingDOAPIRequest
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import javax.inject.Inject

class TMDBRepositoryImplUsingDOAPIRequest @Inject constructor(
    private val tmdbService: TMDServiceUsingBaseResponse
) : TMDRepositoryUsingDOAPIRequest {

    // ------------------- ACCOUNT -------------------

    override suspend fun getAccountDetails(
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<AccountDetails>> =
        safeApiCall { tmdbService.getAccountDetails(apiKey, sessionId) }

    override suspend fun addFavorite(
        accountId: String,
        apiKey: String,
        sessionId: String,
        favoriteRequest: FavoriteRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.addFavorite(accountId, apiKey, sessionId, favoriteRequest) }

    override suspend fun addToWatchlist(
        accountId: String,
        apiKey: String,
        sessionId: String,
        watchlistRequest: WatchlistRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.addToWatchlist(accountId, apiKey, sessionId, watchlistRequest) }

    override suspend fun getFavoriteMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.getFavoriteMovies(accountId, apiKey, sessionId) }

    override suspend fun getFavoriteTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.getFavoriteTVShows(accountId, apiKey, sessionId) }

    override suspend fun getRatedMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.getRatedMovies(accountId, apiKey, sessionId) }

    override suspend fun getRatedTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.getRatedTVShows(accountId, apiKey, sessionId) }

    override suspend fun getWatchlistMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.getWatchlistMovies(accountId, apiKey, sessionId) }

    override suspend fun getWatchlistTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.getWatchlistTVShows(accountId, apiKey, sessionId) }

    // ------------------- AUTHENTICATION -------------------

    override suspend fun createGuestSession(apiKey: String): Flow<ResourceUsingDOAPIRequest<SessionResponse>> =
        safeApiCall { tmdbService.createGuestSession(apiKey) }

    override suspend fun createRequestToken(apiKey: String): Flow<ResourceUsingDOAPIRequest<RequestTokenResponse>> =
        safeApiCall { tmdbService.createRequestToken(apiKey) }

    override suspend fun createSession(
        apiKey: String,
        requestToken: RequestToken
    ): Flow<ResourceUsingDOAPIRequest<SessionResponse>> =
        safeApiCall { tmdbService.createSession(apiKey, requestToken) }

    override suspend fun deleteSession(
        apiKey: String,
        session: Session
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.deleteSession(apiKey, session) }

    // ------------------- CERTIFICATIONS -------------------

    override suspend fun getMovieCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>> =
        safeApiCall { tmdbService.getMovieCertifications(apiKey) }

    override suspend fun getTVCertifications(apiKey: String): Flow<ResourceUsingDOAPIRequest<CertificationResponse>> =
        safeApiCall { tmdbService.getTVCertifications(apiKey) }

    // ------------------- MOVIES -------------------

    override suspend fun getPopularMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.getPopularMovies(apiKey, language, page) }

    override suspend fun getMovieDetails(
        movieId: Int,
        apiKey: String
    ): Flow<ResourceUsingDOAPIRequest<MovieDetailResponse>> =
        safeApiCall { tmdbService.getMovieDetails(movieId, apiKey) }

    override suspend fun rateMovie(
        movieId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.rateMovie(movieId, apiKey, sessionId, ratingRequest) }

    override suspend fun deleteMovieRating(
        movieId: Int,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.deleteRating(movieId, apiKey, sessionId) }

    // ------------------- TV -------------------

    override suspend fun getPopularTVShows(
        apiKey: String,
        language: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.getPopularTVShows(apiKey, language, page) }

    override suspend fun getTVDetails(
        tvId: Int,
        apiKey: String
    ): Flow<ResourceUsingDOAPIRequest<TVDetailResponse>> =
        safeApiCall { tmdbService.getTVDetails(tvId, apiKey) }

    override suspend fun rateTVShow(
        tvId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.rateTVShow(tvId, apiKey, sessionId, ratingRequest) }

    override suspend fun deleteTVRating(
        tvId: Int,
        apiKey: String,
        sessionId: String
    ): Flow<ResourceUsingDOAPIRequest<ResponseStatus>> =
        safeApiCall { tmdbService.deleteTVRating(tvId, apiKey, sessionId) }

    // ------------------- DISCOVER -------------------

    override suspend fun discoverMovies(
        apiKey: String,
        language: String,
        sortBy: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.discoverMovies(apiKey, language, sortBy, page) }

    override suspend fun discoverTVShows(
        apiKey: String,
        language: String,
        sortBy: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.discoverTVShows(apiKey, language, sortBy, page) }

    // ------------------- SEARCH -------------------

    override suspend fun searchMovies(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<MovieListResponse>> =
        safeApiCall { tmdbService.searchMovies(apiKey, query, language, page) }

    override suspend fun searchTVShows(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<TVListResponse>> =
        safeApiCall { tmdbService.searchTVShows(apiKey, query, language, page) }

    override suspend fun searchPeople(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<ResourceUsingDOAPIRequest<PeopleListResponse>> =
        safeApiCall { tmdbService.searchPeople(apiKey, query, language, page) }

    // ------------------- UPLOAD -------------------

    override suspend fun uploadImage(file: MultipartBody.Part): Flow<ResourceUsingDOAPIRequest<UploadResponse>> =
        safeApiCall { tmdbService.uploadImage(file) }
}
