package com.example.newsapp.data.remote

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
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBService {

    // ------------------- ACCOUNT -------------------

    @GET("account")
    suspend fun getAccountDetails(
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): AccountDetails

    @POST("account/{account_id}/favorite")
    suspend fun addFavorite(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body favoriteRequest: FavoriteRequest
    ): ResponseStatus

    @POST("account/{account_id}/watchlist")
    suspend fun addToWatchlist(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body watchlistRequest: WatchlistRequest
    ): ResponseStatus

    @GET("account/{account_id}/favorite/movies")
    suspend fun getFavoriteMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): MovieListResponse

    @GET("account/{account_id}/favorite/tv")
    suspend fun getFavoriteTVShows(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): TVListResponse

    @GET("account/{account_id}/rated/movies")
    suspend fun getRatedMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): MovieListResponse

    @GET("account/{account_id}/rated/tv")
    suspend fun getRatedTVShows(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): TVListResponse

    @GET("account/{account_id}/watchlist/movies")
    suspend fun getWatchlistMovies(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): MovieListResponse

    @GET("account/{account_id}/watchlist/tv")
    suspend fun getWatchlistTVShows(
        @Path("account_id") accountId: String,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): TVListResponse


    // ------------------- AUTHENTICATION -------------------

    @GET("authentication/guest_session/new")
    suspend fun createGuestSession(
        @Query("api_key") apiKey: String
    ): SessionResponse

    @GET("authentication/token/new")
    suspend fun createRequestToken(
        @Query("api_key") apiKey: String
    ): RequestTokenResponse

    @POST("authentication/session/new")
    suspend fun createSession(
        @Query("api_key") apiKey: String,
        @Body requestToken: RequestToken
    ): SessionResponse

    @DELETE("authentication/session")
    suspend fun deleteSession(
        @Query("api_key") apiKey: String,
        @Body session: Session
    ): ResponseStatus


    // ------------------- CERTIFICATIONS -------------------

    @GET("certification/movie/list")
    suspend fun getMovieCertifications(
        @Query("api_key") apiKey: String
    ): CertificationResponse

    @GET("certification/tv/list")
    suspend fun getTVCertifications(
        @Query("api_key") apiKey: String
    ): CertificationResponse


    // ------------------- MOVIES -------------------

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse

    @POST("movie/{movie_id}/rating")
    suspend fun rateMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body ratingRequest: RatingRequest
    ): ResponseStatus

    @DELETE("movie/{movie_id}/rating")
    suspend fun deleteRating(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): ResponseStatus


    // ------------------- TV -------------------

    @GET("tv/popular")
    suspend fun getPopularTVShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TVListResponse

    @GET("tv/{tv_id}")
    suspend fun getTVDetails(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String
    ): TVDetailResponse

    @POST("tv/{tv_id}/rating")
    suspend fun rateTVShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String,
        @Body ratingRequest: RatingRequest
    ): ResponseStatus

    @DELETE("tv/{tv_id}/rating")
    suspend fun deleteTVRating(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("session_id") sessionId: String
    ): ResponseStatus


    // ------------------- DISCOVER -------------------

    @GET("discover/movie")
    suspend fun discoverMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): MovieListResponse

    @GET("discover/tv")
    suspend fun discoverTVShows(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("sort_by") sortBy: String = "popularity.desc",
        @Query("page") page: Int = 1
    ): TVListResponse


    // ------------------- SEARCH -------------------

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): MovieListResponse


    @GET("search/tv")
    suspend fun searchTVShows(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): TVListResponse

    @GET("search/person")
    suspend fun searchPeople(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int = 1
    ): PeopleListResponse

    @Multipart
    @POST("upload") // Replace with your actual endpoint
    suspend fun uploadImage(@Part file: MultipartBody.Part): Response<UploadResponse>

}
