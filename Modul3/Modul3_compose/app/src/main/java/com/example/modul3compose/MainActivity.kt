package com.example.modul3compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.modul3compose.ui.theme.BackgroundPink
import com.example.modul3compose.ui.theme.Modul3ComposeTheme

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
            Modul3ComposeTheme {
                val navController = rememberNavController()

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
                            Home(onCardClick = { cardId ->
                                navController.navigate("detail/$cardId")
                            })
                        }

                        composable(
                            route = "detail/{cardId}",
                            arguments = listOf(navArgument("cardId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val cardId = backStackEntry.arguments?.getInt("cardId") ?: -1
                            DetailScreen(cardId = cardId)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier = Modifier, onCardClick: (Int) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.featured),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(CardData) { item ->
                ItemsCard(
                    card = item,
                    modifier = Modifier.height(150.dp),
                    onButton1Click = onCardClick
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.all_content),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyColumn(modifier = Modifier.weight(1f)) {
            items(CardData) { item ->
                ItemsCard(
                    card = item,
                    modifier = Modifier.fillMaxWidth(),
                    onButton1Click = onCardClick
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}