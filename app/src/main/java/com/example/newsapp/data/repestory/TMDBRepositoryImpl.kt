package com.example.newsapp.data.repestory

import com.example.newsapp.data.models.AccountDetails
import com.example.newsapp.data.models.CertificationResponse
import com.example.newsapp.data.models.FavoriteRequest
import com.example.newsapp.data.models.MovieDetailResponse
import com.example.newsapp.data.models.MovieListResponse
import com.example.newsapp.data.models.PeopleListResponse
import com.example.newsapp.data.models.RatingRequest
import com.example.newsapp.data.models.RequestToken
import com.example.newsapp.data.models.RequestTokenResponse
import com.example.newsapp.data.models.Resource
import com.example.newsapp.data.models.ResponseStatus
import com.example.newsapp.data.models.Session
import com.example.newsapp.data.models.SessionResponse
import com.example.newsapp.data.models.TVDetailResponse
import com.example.newsapp.data.models.TVListResponse
import com.example.newsapp.data.models.UploadResponse
import com.example.newsapp.data.models.WatchlistRequest
import com.example.newsapp.data.remote.TMDBService
import com.example.newsapp.domain.repository.TMDBRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MultipartBody
import javax.inject.Inject

class TMDBRepositoryImpl @Inject constructor(
    private val tmdbService: TMDBService
) : TMDBRepository {

    // ------------------- ACCOUNT -------------------

    override fun getAccountDetails(
        apiKey: String,
        sessionId: String
    ): Flow<Resource<AccountDetails>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getAccountDetails(apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch account details: ${e.message}"))
        }
    }

    override fun addFavorite(
        accountId: String,
        apiKey: String,
        sessionId: String,
        favoriteRequest: FavoriteRequest
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.addFavorite(accountId, apiKey, sessionId, favoriteRequest)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to add favorite: ${e.message}"))
        }
    }

    override fun addToWatchlist(
        accountId: String,
        apiKey: String,
        sessionId: String,
        watchlistRequest: WatchlistRequest
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response =
                tmdbService.addToWatchlist(accountId, apiKey, sessionId, watchlistRequest)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to add to watchlist: ${e.message}"))
        }
    }

    override fun getFavoriteMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getFavoriteMovies(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch favorite movies: ${e.message}"))
        }
    }

    override fun getFavoriteTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getFavoriteTVShows(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch favorite TV shows: ${e.message}"))
        }
    }

    override fun getRatedMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getRatedMovies(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch rated movies: ${e.message}"))
        }
    }

    override fun getRatedTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getRatedTVShows(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch rated TV shows: ${e.message}"))
        }
    }

    override fun getWatchlistMovies(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getWatchlistMovies(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch watchlist movies: ${e.message}"))
        }
    }

    override fun getWatchlistTVShows(
        accountId: String,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getWatchlistTVShows(accountId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch watchlist TV shows: ${e.message}"))
        }
    }

    // ------------------- AUTHENTICATION -------------------

    override fun createGuestSession(apiKey: String): Flow<Resource<SessionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.createGuestSession(apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to create guest session: ${e.message}"))
        }
    }

    override fun createRequestToken(apiKey: String): Flow<Resource<RequestTokenResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.createRequestToken(apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to create request token: ${e.message}"))
        }
    }

    override fun createSession(
        apiKey: String,
        requestToken: RequestToken
    ): Flow<Resource<SessionResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.createSession(apiKey, requestToken)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to create session: ${e.message}"))
        }
    }

    override fun deleteSession(apiKey: String, session: Session): Flow<Resource<ResponseStatus>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = tmdbService.deleteSession(apiKey, session)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to delete session: ${e.message}"))
            }
        }

    // ------------------- CERTIFICATIONS -------------------

    override fun getMovieCertifications(apiKey: String): Flow<Resource<CertificationResponse>> =
        flow {
            emit(Resource.Loading())
            try {
                val response = tmdbService.getMovieCertifications(apiKey)
                emit(Resource.Success(response))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to fetch movie certifications: ${e.message}"))
            }
        }

    override fun getTVCertifications(apiKey: String): Flow<Resource<CertificationResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getTVCertifications(apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch TV certifications: ${e.message}"))
        }
    }

    // ------------------- MOVIES -------------------

    override fun getPopularMovies(
        apiKey: String,
        language: String,
        page: Int
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getPopularMovies(apiKey, language, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch popular movies: ${e.message}"))
        }
    }

    override fun getMovieDetails(
        movieId: Int,
        apiKey: String
    ): Flow<Resource<MovieDetailResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getMovieDetails(movieId, apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch movie details: ${e.message}"))
        }
    }

    override fun rateMovie(
        movieId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.rateMovie(movieId, apiKey, sessionId, ratingRequest)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to rate movie: ${e.message}"))
        }
    }

    override fun deleteMovieRating(
        movieId: Int,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.deleteRating(movieId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to delete movie rating: ${e.message}"))
        }
    }

    // ------------------- TV -------------------

    override fun getPopularTVShows(
        apiKey: String,
        language: String,
        page: Int
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getPopularTVShows(apiKey, language, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch popular TV shows: ${e.message}"))
        }
    }

    override fun getTVDetails(tvId: Int, apiKey: String): Flow<Resource<TVDetailResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.getTVDetails(tvId, apiKey)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to fetch TV details: ${e.message}"))
        }
    }

    override fun rateTVShow(
        tvId: Int,
        apiKey: String,
        sessionId: String,
        ratingRequest: RatingRequest
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.rateTVShow(tvId, apiKey, sessionId, ratingRequest)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to rate TV show: ${e.message}"))
        }
    }

    override fun deleteTVRating(
        tvId: Int,
        apiKey: String,
        sessionId: String
    ): Flow<Resource<ResponseStatus>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.deleteTVRating(tvId, apiKey, sessionId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to delete TV rating: ${e.message}"))
        }
    }

    // ------------------- DISCOVER -------------------

    override fun discoverMovies(
        apiKey: String,
        language: String,
        sortBy: String,
        page: Int
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.discoverMovies(apiKey, language, sortBy, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to discover movies: ${e.message}"))
        }
    }

    override fun discoverTVShows(
        apiKey: String,
        language: String,
        sortBy: String,
        page: Int
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.discoverTVShows(apiKey, language, sortBy, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to discover TV shows: ${e.message}"))
        }
    }

    // ------------------- SEARCH -------------------

    override fun searchMovies(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<Resource<MovieListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.searchMovies(apiKey, query, language, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to search movies: ${e.message}"))
        }
    }

    override fun searchTVShows(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<Resource<TVListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.searchTVShows(apiKey, query, language, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to search TV shows: ${e.message}"))
        }
    }

    override fun searchPeople(
        apiKey: String,
        query: String,
        language: String,
        page: Int
    ): Flow<Resource<PeopleListResponse>> = flow {
        emit(Resource.Loading())
        try {
            val response = tmdbService.searchPeople(apiKey, query, language, page)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error("Failed to search people: ${e.message}"))
        }
    }


   override fun uploadImage(file: MultipartBody.Part): Flow<Resource<UploadResponse>> = flow {
        emit(Resource.Loading())  // Emit loading state
        try {
            val response = tmdbService.uploadImage(file)
            if (response.isSuccessful) {
                response.body()?.let { uploadResponse ->
                    emit(Resource.Success(uploadResponse)) // Emit success state with data
                } ?: emit(Resource.Error("Upload failed: Empty response body"))
            } else {
                emit(Resource.Error("Upload failed: ${response.message()}")) // Emit error with message
            }
        } catch (e: Exception) {
            emit(Resource.Error("Failed to upload image: ${e.message}")) // Emit error on exception
        }
    }
}
