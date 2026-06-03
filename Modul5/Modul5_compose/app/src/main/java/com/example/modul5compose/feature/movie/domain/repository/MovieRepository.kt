package com.example.modul5compose.feature.movie.domain.repository

import com.example.modul5compose.core.network.ApiResult
import com.example.modul5compose.feature.movie.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<ApiResult<List<Movie>>>

    suspend fun getMovieById(movieId: Int): Movie?
}
