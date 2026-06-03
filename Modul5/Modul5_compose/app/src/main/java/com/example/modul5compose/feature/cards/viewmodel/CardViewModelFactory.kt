package com.example.modul5compose.feature.cards.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CardViewModelFactory(private val category: String) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>) : T {
        if (modelClass.isAssignableFrom(CardViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CardViewModel(category) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


