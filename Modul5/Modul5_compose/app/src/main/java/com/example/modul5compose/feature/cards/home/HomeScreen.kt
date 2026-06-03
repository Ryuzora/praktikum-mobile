package com.example.modul5compose.feature.cards.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.modul5compose.R
import com.example.modul5compose.feature.cards.data.ContentCard
import com.example.modul5compose.feature.cards.components.ItemsCard

@Composable
fun HomeScreen(modifier: Modifier = Modifier, cards: List<ContentCard>, onCardClick: (Int) -> Unit) {
    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.featured),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.size(8.dp))
        LazyRow {
            items(cards) { item ->
                ItemsCard(
                    card = item, modifier = Modifier.height(150.dp), onButton1Click = onCardClick
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
            items(cards) { item ->
                ItemsCard(
                    card = item, modifier = Modifier.fillMaxWidth(), onButton1Click = onCardClick
                )
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

