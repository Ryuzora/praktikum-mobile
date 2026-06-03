package com.example.modul5compose.feature.movie.data.mapper

import com.example.modul5compose.feature.movie.data.local.MovieEntity
import com.example.modul5compose.feature.movie.data.remote.dto.MovieDto
import com.example.modul5compose.feature.movie.domain.model.Movie

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

fun MovieDto.toMovie(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { IMAGE_BASE_URL + it },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0
    )
}

fun List<MovieDto>.toMovieList(): List<Movie> {
    return map { it.toMovie() }
}

fun Movie.toEntity(): MovieEntity {
    return MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<Movie>.toEntityList(): List<MovieEntity> {
    return map { it.toEntity() }
}

fun MovieEntity.toMovie(): Movie {
    return Movie(
        id = id,
        title = title,
        overview = overview,
        posterUrl = posterUrl,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}

fun List<MovieEntity>.toMovieListFromEntity(): List<Movie> {
    return map { it.toMovie() }
}
