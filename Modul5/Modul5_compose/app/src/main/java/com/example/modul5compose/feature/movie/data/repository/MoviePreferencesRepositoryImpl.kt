package com.example.modul5compose.feature.movie.data.repository

import com.example.modul5compose.core.preferences.AppPreferences
import com.example.modul5compose.feature.movie.domain.repository.MoviePreferencesRepository

class MoviePreferencesRepositoryImpl(
    private val appPreferences: AppPreferences
) : MoviePreferencesRepository {

    override fun saveLastOpenedMovie(movieId: Int, movieTitle: String) {
        appPreferences.saveLastOpenedMovie(movieId, movieTitle)
    }

    override fun getLastOpenedMovieTitle(): String {
        return appPreferences.getLastOpenedMovieTitle()
    }
}
