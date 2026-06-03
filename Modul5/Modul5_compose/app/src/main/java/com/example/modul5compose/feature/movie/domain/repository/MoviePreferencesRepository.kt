package com.example.modul5compose.feature.movie.domain.repository

interface MoviePreferencesRepository {
    fun saveLastOpenedMovie(movieId: Int, movieTitle: String)

    fun getLastOpenedMovieTitle(): String
}
