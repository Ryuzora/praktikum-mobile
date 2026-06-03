package com.example.modul5compose.feature.movie.domain.usecase

import com.example.modul5compose.feature.movie.domain.repository.MoviePreferencesRepository

class GetLastOpenedMovieTitleUseCase(
    private val moviePreferencesRepository: MoviePreferencesRepository
) {
    operator fun invoke(): String {
        return moviePreferencesRepository.getLastOpenedMovieTitle()
    }
}
