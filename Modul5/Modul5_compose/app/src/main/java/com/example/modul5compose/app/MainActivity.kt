package com.example.modul5compose.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.modul5compose.core.database.AppDatabase
import com.example.modul5compose.core.preferences.AppPreferences
import com.example.modul5compose.ui.theme.BackgroundPink
import com.example.modul5compose.ui.theme.Modul5ComposeTheme
import com.example.modul5compose.feature.movie.data.repository.MoviePreferencesRepositoryImpl
import com.example.modul5compose.feature.movie.data.repository.MovieRepositoryImpl
import com.example.modul5compose.feature.movie.domain.usecase.GetLastOpenedMovieTitleUseCase
import com.example.modul5compose.feature.movie.domain.usecase.GetMovieByIdUseCase
import com.example.modul5compose.feature.movie.domain.usecase.GetPopularMoviesUseCase
import com.example.modul5compose.feature.movie.domain.usecase.SaveLastOpenedMovieUseCase
import com.example.modul5compose.feature.movie.presentation.screens.MovieDetailScreen
import com.example.modul5compose.feature.movie.presentation.screens.MovieScreen
import com.example.modul5compose.feature.movie.presentation.viewmodel.MovieViewModel
import com.example.modul5compose.feature.movie.presentation.viewmodel.MovieViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )
        setContent {
            Modul5ComposeTheme {
                val navController = rememberNavController()
                val database = AppDatabase.getInstance(applicationContext)
                val movieRepository = MovieRepositoryImpl(database.movieDao())
                val moviePreferencesRepository = MoviePreferencesRepositoryImpl(
                    AppPreferences(applicationContext)
                )
                val movieViewModel: MovieViewModel = viewModel(
                    factory = MovieViewModelFactory(
                        getPopularMoviesUseCase = GetPopularMoviesUseCase(movieRepository),
                        getMovieByIdUseCase = GetMovieByIdUseCase(movieRepository),
                        saveLastOpenedMovieUseCase = SaveLastOpenedMovieUseCase(
                            moviePreferencesRepository
                        ),
                        getLastOpenedMovieTitleUseCase = GetLastOpenedMovieTitleUseCase(
                            moviePreferencesRepository
                        )
                    )
                )

                Scaffold(modifier = Modifier.fillMaxSize(), containerColor = BackgroundPink) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier
                            .padding(innerPadding)
                            .background(BackgroundPink)
                            .padding(5.dp)
                    ) {
                        composable(route = "home") {
                            MovieScreen(
                                navController = navController,
                                viewModel = movieViewModel
                            )
                        }

                        composable(
                            route = "detail/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: -1
                            MovieDetailScreen(
                                movieId = cardId,
                                viewModel = movieViewModel,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
