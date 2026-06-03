package com.example.modul5compose.feature.movie.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modul5compose.core.common.UiState
import com.example.modul5compose.core.network.ApiResult
import com.example.modul5compose.feature.movie.domain.model.Movie
import com.example.modul5compose.feature.movie.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.modul5compose.feature.movie.domain.usecase.GetMovieByIdUseCase
import com.example.modul5compose.feature.movie.domain.usecase.GetPopularMoviesUseCase
import com.example.modul5compose.feature.movie.domain.usecase.SaveLastOpenedMovieUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val getMovieByIdUseCase: GetMovieByIdUseCase,
    private val saveLastOpenedMovieUseCase: SaveLastOpenedMovieUseCase,
    private val getLastOpenedMovieTitleUseCase: GetLastOpenedMovieTitleUseCase
) : ViewModel() {

    private val _moviesState = MutableStateFlow<UiState<List<Movie>>>(UiState.Idle)
    val moviesState: StateFlow<UiState<List<Movie>>> = _moviesState.asStateFlow()

    private val _selectedMovie = MutableStateFlow<Movie?>(null)
    val selectedMovie: StateFlow<Movie?> = _selectedMovie.asStateFlow()

    private val _lastOpenedMovieTitle = MutableStateFlow("")
    val lastOpenedMovieTitle: StateFlow<String> = _lastOpenedMovieTitle.asStateFlow()

    init {
        loadLastOpenedMovie()
        loadPopularMovies()
    }

    fun loadPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collect { result ->
                when (result) {
                    ApiResult.Loading -> {
                        _moviesState.value = UiState.Loading
                        Timber.d("Loading data film dari TMDB")
                    }

                    is ApiResult.Success -> {
                        _moviesState.value = UiState.Success(result.data)
                        Timber.d("Data film berhasil masuk ke ViewModel. Jumlah: ${result.data.size}")
                    }

                    is ApiResult.Error -> {
                        _moviesState.value = UiState.Error(result.message)
                        Timber.e(result.throwable, "Gagal memuat data film: ${result.message}")
                    }
                }
            }
        }
    }

    fun onDetailClicked(movie: Movie) {
        _selectedMovie.value = movie
        saveLastOpenedMovieUseCase(movieId = movie.id, movieTitle = movie.title)
        _lastOpenedMovieTitle.value = movie.title
        Timber.d("Detail ditekan: id=${movie.id}, title=${movie.title}")
    }

    fun selectMovieById(movieId: Int) {
        viewModelScope.launch {
            val movieFromCurrentState = (_moviesState.value as? UiState.Success)
                ?.data
                ?.find { it.id == movieId }

            _selectedMovie.value = movieFromCurrentState ?: getMovieByIdUseCase(movieId)
            Timber.d("Memilih movie berdasarkan id=$movieId, hasil=${_selectedMovie.value?.title}")
        }
    }

    private fun loadLastOpenedMovie() {
        _lastOpenedMovieTitle.value = getLastOpenedMovieTitleUseCase()
    }
}
