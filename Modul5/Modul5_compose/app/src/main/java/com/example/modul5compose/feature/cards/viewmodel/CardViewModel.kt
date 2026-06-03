package com.example.modul5compose.feature.cards.viewmodel

import androidx.lifecycle.ViewModel
import com.example.modul5compose.feature.cards.data.CardData
import com.example.modul5compose.feature.cards.data.ContentCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CardViewModel(private val category: String) : ViewModel() {
    private val _cards = MutableStateFlow(CardData.getCards())
    val cards: StateFlow<List<ContentCard>> = _cards.asStateFlow()

    private val _selectedCardId = MutableStateFlow<Int?>(null)
    val selectedCardId: StateFlow<Int?> = _selectedCardId.asStateFlow()

    fun onCardClick(cardId: Int) {
        _selectedCardId.value = cardId
    }

    fun onCardNavigated() {
        _selectedCardId.value = null
    }

    fun getCardById(cardId: Int) : ContentCard? {
        return _cards.value.find { it.id == cardId }
    }

    fun getCategoryName(): String {
        return category
    }
}


