package com.example.modul5compose.core.preferences

import android.content.Context
import timber.log.Timber

class AppPreferences(context: Context) {
    private val sharedPreferences = context.applicationContext.getSharedPreferences(
        PREF_NAME,
        Context.MODE_PRIVATE
    )

    fun saveLastOpenedMovie(movieId: Int, movieTitle: String) {
        val isSaved = sharedPreferences.edit()
            .putInt(KEY_LAST_MOVIE_ID, movieId)
            .putString(KEY_LAST_MOVIE_TITLE, movieTitle)
            .commit()

        Timber.d("SharedPreferences save result: $isSaved, movie=$movieTitle")
    }

    fun getLastOpenedMovieId(): Int {
        return sharedPreferences.getInt(KEY_LAST_MOVIE_ID, -1)
    }

    fun getLastOpenedMovieTitle(): String {
        return sharedPreferences.getString(KEY_LAST_MOVIE_TITLE, "").orEmpty()
    }

    companion object {
        private const val PREF_NAME = "movie_preferences"
        private const val KEY_LAST_MOVIE_ID = "last_movie_id"
        private const val KEY_LAST_MOVIE_TITLE = "last_movie_title"
    }
}
