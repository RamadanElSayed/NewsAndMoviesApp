package com.example.newsapp.presentabon.moviescreen.uimodel

sealed class TMDBIntent {
    data class InitializeSession(val apiKey: String) : TMDBIntent()

    // Movies
    data class LoadPopularMovies(val apiKey: String, val language: String, val page: Int) : TMDBIntent()
    data class LoadMovieDetails(val movieId: Int, val apiKey: String) : TMDBIntent()
    data class SearchMovies(val query: String, val apiKey: String, val language: String, val page: Int) : TMDBIntent()
    data class RateMovie(val movieId: Int, val apiKey: String, val sessionId: String, val rating: Double) : TMDBIntent()
    data class AddMovieToWatchlist(val accountId: String, val apiKey: String, val sessionId: String, val movieId: Int) : TMDBIntent()

    // TV Shows
    data class LoadPopularTVShows(val apiKey: String, val language: String, val page: Int) : TMDBIntent()
    data class LoadTVDetails(val tvId: Int, val apiKey: String) : TMDBIntent()
    data class RateTVShow(val tvId: Int, val apiKey: String, val sessionId: String, val rating: Double) : TMDBIntent()
    data class AddTVToWatchlist(val accountId: String, val apiKey: String, val sessionId: String, val tvId: Int) : TMDBIntent()

    // Account
    data class LoadAccountDetails(val apiKey: String, val sessionId: String) : TMDBIntent()
    data class LoadFavoriteMovies(val accountId: String, val apiKey: String, val sessionId: String) : TMDBIntent()
    data class LoadWatchlistMovies(val accountId: String, val apiKey: String, val sessionId: String) : TMDBIntent()

    data class AddFavorite(val accountId: String, val apiKey: String, val sessionId: String, val mediaId: Int, val mediaType: String, val favorite: Boolean) : TMDBIntent()

}
