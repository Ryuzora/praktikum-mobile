package com.example.modul5compose.feature.movie.data.repository

import com.example.modul5compose.BuildConfig
import com.example.modul5compose.core.network.ApiClient
import com.example.modul5compose.core.network.ApiResult
import com.example.modul5compose.core.network.safeApiCall
import com.example.modul5compose.feature.movie.data.local.MovieDao
import com.example.modul5compose.feature.movie.data.mapper.toEntityList
import com.example.modul5compose.feature.movie.data.mapper.toMovie
import com.example.modul5compose.feature.movie.data.mapper.toMovieList
import com.example.modul5compose.feature.movie.data.mapper.toMovieListFromEntity
import com.example.modul5compose.feature.movie.data.remote.MovieApiService
import com.example.modul5compose.feature.movie.domain.model.Movie
import com.example.modul5compose.feature.movie.domain.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class MovieRepositoryImpl(
    private val movieDao: MovieDao,
    private val apiService: MovieApiService = ApiClient.movieApiService
) : MovieRepository {

    override fun getPopularMovies(): Flow<ApiResult<List<Movie>>> = flow {
        emit(ApiResult.Loading)

        val cachedMovies = movieDao.getMovies()
            .first()
            .toMovieListFromEntity()

        if (cachedMovies.isNotEmpty()) {
            Timber.d("Cache Room ditemukan. Jumlah data: ${cachedMovies.size}")
            emit(ApiResult.Success(cachedMovies))
        } else {
            Timber.d("Cache Room masih kosong")
        }

        if (BuildConfig.TMDB_API_KEY.isBlank()) {
            if (cachedMovies.isEmpty()) {
                emit(
                    ApiResult.Error(
                        message = "TMDB API key belum diisi. Tambahkan TMDB_API_KEY di local.properties."
                    )
                )
            }
            return@flow
        }

        val result = safeApiCall {
            apiService.getPopularMovies(apiKey = BuildConfig.TMDB_API_KEY)
        }

        when (result) {
            is ApiResult.Success -> {
                val remoteMovies = result.data.results.toMovieList()

                Timber.d("Berhasil mengambil data dari TMDB. Jumlah data: ${remoteMovies.size}")

                movieDao.clearMovies()
                movieDao.insertMovies(remoteMovies.toEntityList())

                Timber.d("Data terbaru berhasil disimpan ke Room cache")
                emit(ApiResult.Success(remoteMovies))
            }

            is ApiResult.Error -> {
                Timber.e(result.throwable, "Gagal mengambil data dari TMDB: ${result.message}")

                if (cachedMovies.isEmpty()) {
                    emit(result)
                } else {
                    Timber.d("API gagal, tetapi cache tersedia. UI tetap memakai data dari Room")
                }
            }

            ApiResult.Loading -> emit(ApiResult.Loading)
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getMovieById(movieId: Int): Movie? {
        return movieDao.getMovieById(movieId)?.toMovie()
    }
}
