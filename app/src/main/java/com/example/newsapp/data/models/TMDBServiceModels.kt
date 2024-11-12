package com.example.newsapp.data.models

import com.google.gson.annotations.SerializedName

// ------------------- GENERAL RESPONSE MODELS -------------------

data class ResponseStatus(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String
)

data class CertificationResponse(
    @SerializedName("certifications") val certifications: Map<String, List<Certification>>
)

data class Certification(
    @SerializedName("certification") val certification: String,
    @SerializedName("meaning") val meaning: String,
    @SerializedName("order") val order: Int
)

data class RequestTokenResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("expires_at") val expiresAt: String,
    @SerializedName("request_token") val requestToken: String
)

data class SessionResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("session_id") val sessionId: String?
)

data class RequestToken(
    @SerializedName("request_token") val requestToken: String
)

data class Session(
    @SerializedName("session_id") val sessionId: String
)

// ------------------- ACCOUNT MODELS -------------------

data class AccountDetails(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("username") val username: String,
    @SerializedName("include_adult") val includeAdult: Boolean,
    @SerializedName("iso_639_1") val iso6391: String,
    @SerializedName("iso_3166_1") val iso31661: String,
    @SerializedName("avatar") val avatar: Avatar
)

data class Avatar(
    @SerializedName("gravatar") val gravatar: Gravatar
)

data class Gravatar(
    @SerializedName("hash") val hash: String
)

data class FavoriteRequest(
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("favorite") val favorite: Boolean
)

data class WatchlistRequest(
    @SerializedName("media_type") val mediaType: String,
    @SerializedName("media_id") val mediaId: Int,
    @SerializedName("watchlist") val watchlist: Boolean
)

// ------------------- MOVIE MODELS -------------------

data class MovieListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Movie>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class Movie(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("video") val video: Boolean
)

data class MovieDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?
)

// ------------------- TV SHOW MODELS -------------------

data class TVListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<TVShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class TVShow(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String
)

data class TVDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: List<Genre>,
    @SerializedName("number_of_seasons") val numberOfSeasons: Int,
    @SerializedName("number_of_episodes") val numberOfEpisodes: Int,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?
)

// ------------------- RATING MODELS -------------------

data class RatingRequest(
    @SerializedName("value") val value: Double // Range: 0.5 to 10.0
)

// ------------------- DISCOVER MODELS -------------------

data class DiscoverResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<MovieOrTVShow>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class MovieOrTVShow(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("name") val name: String?,
    @SerializedName("overview") val overview: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("first_air_date") val firstAirDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
    @SerializedName("original_language") val originalLanguage: String
)

// ------------------- SEARCH MODELS -------------------

data class SearchResponse<T>(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

// ------------------- PEOPLE MODELS -------------------

data class PeopleListResponse(
    @SerializedName("page") val page: Int,
    @SerializedName("results") val results: List<Person>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)

data class Person(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("known_for_department") val knownForDepartment: String,
    @SerializedName("known_for") val knownFor: List<MovieOrTVShow>
)

// ------------------- GENRES -------------------

data class Genre(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String
)

// ------------------- LIST MODELS -------------------

data class ListDetailResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("items") val items: List<MovieOrTVShow>,
    @SerializedName("item_count") val itemCount: Int,
    @SerializedName("iso_639_1") val iso6391: String
)

data class CreateListRequest(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("language") val language: String
)

data class ListCreateResponse(
    @SerializedName("status_code") val statusCode: Int,
    @SerializedName("status_message") val statusMessage: String,
    @SerializedName("list_id") val listId: Int
)

data class MediaItemRequest(
    @SerializedName("media_id") val mediaId: Int
)



data class UploadResponse(
    @SerializedName("message") val message: String,
    @SerializedName("file_url") val fileUrl: String
)