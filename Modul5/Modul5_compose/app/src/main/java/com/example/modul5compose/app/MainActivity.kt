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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.modul5compose.ui.theme.BackgroundPink
import com.example.modul5compose.ui.theme.Modul5ComposeTheme
import com.example.modul5compose.feature.cards.detail.DetailScreen
import com.example.modul5compose.feature.cards.home.HomeScreen
import com.example.modul5compose.feature.cards.viewmodel.CardViewModel
import com.example.modul5compose.feature.cards.viewmodel.CardViewModelFactory
import timber.log.Timber

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
                val cardViewModel: CardViewModel = viewModel(factory = CardViewModelFactory("Main"))

                val cards by cardViewModel.cards.collectAsState()
                val selectedCardId by cardViewModel.selectedCardId.collectAsState()

                LaunchedEffect(selectedCardId) {
                    val cardId = selectedCardId
                    if (cardId != null) {
                        val selectedCard = cardViewModel.getCardById(cardId)
                        if (selectedCard != null) {
                            Timber.i(
                                "Navigate to detail: id=%d title=%s age=%d",
                                selectedCard.id,
                                selectedCard.title,
                                selectedCard.age
                            )
                        } else {
                            Timber.w("Navigate to detail: card not found for id=%d", cardId)
                        }
                        navController.navigate("detail/$cardId")
                        cardViewModel.onCardNavigated()
                    }
                }

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
                            HomeScreen(
                                cards = cards,
                                onCardClick = { clickedId ->
                                    cardViewModel.onCardClick(clickedId)
                                }
                            )
                        }

                        composable(
                            route = "detail/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: -1
                            DetailScreen(cardId = cardId, viewModel = cardViewModel)
                        }
                    }
                }
            }
        }
    }
}
