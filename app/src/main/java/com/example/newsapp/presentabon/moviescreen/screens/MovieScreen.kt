package com.example.newsapp.presentabon.moviescreen.screens


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.PlaylistAdd
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsapp.data.models.Movie
import com.example.newsapp.presentabon.moviescreen.uimodel.TMDBIntent
import com.example.newsapp.presentabon.moviescreen.viewmodel.TMDBViewModel
import kotlinx.coroutines.launch

@Composable
fun MovieScreen(viewModel: TMDBViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    val scope = rememberCoroutineScope()

    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    var showRatingDialog by remember { mutableStateOf(false) }
    var selectedMovieId by remember { mutableStateOf<Int?>(null) }

    // Initialize session when screen loads
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.intentChannel.send(TMDBIntent.InitializeSession(apiKey = "e0da98cf559a90e56f78a06cc63600e8"))
        }
//        scope.launch {
//            viewModel.intentChannel.send(
//                TMDBIntent.LoadPopularMovies(
//                    apiKey = "e0da98cf559a90e56f78a06cc63600e8",
//                    language = "en-US",
//                    page = 1
//                )
//            )
//        }
    }

    // Initialize session when screen loads
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.intentChannel.send(TMDBIntent.InitializeSession(apiKey = "e0da98cf559a90e56f78a06cc63600e8"))
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Movie Explorer",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query ->
                searchQuery = query
                if (query.text.isNotEmpty()) {
                    scope.launch {
                        viewModel.intentChannel.send(
                            TMDBIntent.SearchMovies(
                                query = query.text,
                                apiKey = "e0da98cf559a90e56f78a06cc63600e8",
                                language = "en-US",
                                page = 1
                            )
                        )
                    }
                }
            },
            label = { Text("Search Movies") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.errorMessage != null -> {
                Text("Error: ${state.errorMessage}", color = MaterialTheme.colorScheme.error)
            }

            else -> {
                MovieList(
                    movies = state.searchedMovies?.results
                        ?: state.popularMovies?.results.orEmpty(),
                    onAddToWatchlist = { movieId ->
                        scope.launch {
                            viewModel.intentChannel.send(
                                TMDBIntent.AddMovieToWatchlist(
                                    accountId = "YOUR_ACCOUNT_ID",
                                    apiKey = "e0da98cf559a90e56f78a06cc63600e8",
                                    sessionId = "YOUR_SESSION_ID",
                                    movieId = movieId
                                )
                            )
                        }
                    },
                    onFavorite = { movieId ->
                        scope.launch {
                            viewModel.intentChannel.send(
                                TMDBIntent.AddFavorite(
                                    accountId = "YOUR_ACCOUNT_ID",
                                    apiKey = "e0da98cf559a90e56f78a06cc63600e8",
                                    sessionId = "YOUR_SESSION_ID",
                                    mediaId = movieId,
                                    mediaType = "movie",
                                    favorite = true
                                )
                            )
                        }
                    },
                    onRateMovie = { movieId ->
                        selectedMovieId = movieId
                        showRatingDialog = true
                    }
                )
            }
        }

        if (showRatingDialog && selectedMovieId != null) {
            RatingDialog(
                movieId = selectedMovieId!!,
                onDismiss = { showRatingDialog = false },
                onRate = { rating ->
                    scope.launch {
                        viewModel.intentChannel.send(
                            TMDBIntent.RateMovie(
                                movieId = selectedMovieId!!,
                                apiKey = "e0da98cf559a90e56f78a06cc63600e8",
                                sessionId = "YOUR_SESSION_ID",
                                rating = rating
                            )
                        )
                        showRatingDialog = false
                    }
                }
            )
        }
    }
}

@Composable
fun MovieList(
    movies: List<Movie>,
    onAddToWatchlist: (Int) -> Unit,
    onFavorite: (Int) -> Unit,
    onRateMovie: (Int) -> Unit
) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(movies) { movie ->
            MovieItem(
                movie = movie,
                onAddToWatchlist = { onAddToWatchlist(movie.id) },
                onFavorite = { onFavorite(movie.id) },
                onRateMovie = { onRateMovie(movie.id) }
            )
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onAddToWatchlist: () -> Unit,
    onFavorite: () -> Unit,
    onRateMovie: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(movie.overview, style = MaterialTheme.typography.bodyMedium, maxLines = 3)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = onAddToWatchlist) {
                    Icon(
                        Icons.AutoMirrored.Filled.PlaylistAdd,
                        contentDescription = "Add to Watchlist"
                    )
                }
                IconButton(onClick = onFavorite) {
                    Icon(Icons.Default.Favorite, contentDescription = "Add to Favorites")
                }
                IconButton(onClick = onRateMovie) {
                    Icon(Icons.Default.Star, contentDescription = "Rate Movie")
                }
            }
        }
    }
}

@Composable
fun RatingDialog(movieId: Int, onDismiss: () -> Unit, onRate: (Double) -> Unit) {
    var rating by remember { mutableStateOf(5.0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Rate Movie") },
        text = {
            Column {
                Text("Select a rating for the movie:")
                Slider(
                    value = rating.toFloat(),
                    onValueChange = { rating = it.toDouble() },
                    valueRange = 0.5f..10f,
                    steps = 19,
                    modifier = Modifier.padding(vertical = 16.dp)
                )
                Text("Rating: ${"%.1f".format(rating)}")
            }
        },
        confirmButton = {
            TextButton(onClick = { onRate(rating) }) {
                Text("Rate")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
