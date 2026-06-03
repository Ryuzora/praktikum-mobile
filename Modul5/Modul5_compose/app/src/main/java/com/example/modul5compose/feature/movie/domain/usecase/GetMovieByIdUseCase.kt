package com.example.modul5compose.feature.movie.domain.usecase

import com.example.modul5compose.feature.movie.domain.repository.MovieRepository

class GetMovieByIdUseCase(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: Int) = movieRepository.getMovieById(movieId)
}
