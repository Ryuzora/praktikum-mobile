package com.example.modul5compose.feature.movie.domain.usecase

import com.example.modul5compose.feature.movie.domain.repository.MoviePreferencesRepository

class SaveLastOpenedMovieUseCase(
    private val moviePreferencesRepository: MoviePreferencesRepository
) {
    operator fun invoke(movieId: Int, movieTitle: String) {
        moviePreferencesRepository.saveLastOpenedMovie(movieId, movieTitle)
    }
}
