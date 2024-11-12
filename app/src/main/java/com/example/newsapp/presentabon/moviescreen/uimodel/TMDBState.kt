package com.example.newsapp.presentabon.moviescreen.uimodel

import com.example.newsapp.data.models.*

data class TMDBState(
    val isLoading: Boolean = false,

    // Movie data
    val popularMovies: MovieListResponse? = null,
    val movieDetail: MovieDetailResponse? = null,
    val searchedMovies: MovieListResponse? = null,
    val movieRatingStatus: ResponseStatus? = null,
    val movieWatchlistStatus: ResponseStatus? = null,

    // TV data
    val popularTVShows: TVListResponse? = null,
    val tvDetail: TVDetailResponse? = null,
    val tvRatingStatus: ResponseStatus? = null,
    val tvWatchlistStatus: ResponseStatus? = null,

    // Account data
    val accountDetails: AccountDetails? = null,
    val favoriteMovies: MovieListResponse? = null,
    val watchlistMovies: MovieListResponse? = null,
    val favoriteStatus: ResponseStatus? = null,

    // Upload data
    val uploadState: UploadResponse? = null,

    // Session data
    val sessionId: String? = null,
    val accountId: String? = null,

    // Error handling
    val errorMessage: String? = null
)
