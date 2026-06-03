package com.example.modul5compose.feature.movie.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.modul5compose.R
import com.example.modul5compose.core.common.UiState
import com.example.modul5compose.feature.movie.presentation.components.MovieListItem
import com.example.modul5compose.feature.movie.presentation.viewmodel.MovieViewModel

@Composable
fun MovieScreen(
    navController: NavController,
    viewModel: MovieViewModel,
    modifier: Modifier = Modifier
) {
    val moviesState by viewModel.moviesState.collectAsState()
    val lastOpenedMovieTitle by viewModel.lastOpenedMovieTitle.collectAsState()

    when (val state = moviesState) {
        UiState.Idle -> {
            CenteredMessage(
                modifier = modifier,
                message = stringResource(R.string.preparing_data)
            )
        }

        UiState.Loading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            LazyColumn(
                modifier = modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.featured_movies_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.padding(bottom = 16.dp)
                    ) {
                        items(state.data.take(5), key = { movie -> movie.id }) { movie ->
                            MovieListItem(
                                movie = movie,
                                modifier = Modifier.width(360.dp),
                                onNavigateToDetail = {
                                    viewModel.onDetailClicked(movie)
                                    navController.navigate("detail/${movie.id}")
                                }
                            )
                        }
                    }
                }

                item {
                    Text(
                        text = stringResource(R.string.popular_movies_title),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )

                    if (lastOpenedMovieTitle.isNotBlank()) {
                        Text(
                            text = stringResource(
                                R.string.last_opened_movie_title,
                                lastOpenedMovieTitle
                            ),
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                        )
                    }
                }

                items(state.data.drop(5), key = { movie -> movie.id }) { movie ->
                    MovieListItem(
                        movie = movie,
                        onNavigateToDetail = {
                            viewModel.onDetailClicked(movie)
                            navController.navigate("detail/${movie.id}")
                        }
                    )
                }
            }
        }

        is UiState.Error -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.error_loading_movies),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = state.message,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(onClick = { viewModel.loadPopularMovies() }) {
                        Text(text = stringResource(R.string.try_again_button))
                    }
                }
            }
        }
    }
}

@Composable
private fun CenteredMessage(
    message: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = message)
    }
}
