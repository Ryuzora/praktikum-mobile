package com.example.modul4xml.viewmodel

import androidx.lifecycle.ViewModel
import com.example.modul4xml.data.CardData
import com.example.modul4xml.data.ContentCard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber

sealed class UiEvent {
    data class OpenDetail(val cardId: Int) : UiEvent()
    data class OpenUrl(val url: String) : UiEvent()
}

class CardViewModel(private val label: String) : ViewModel() {

    private val _items = MutableStateFlow(CardData)
    val items: StateFlow<List<ContentCard>> = _items.asStateFlow()

    private val _event = MutableStateFlow<UiEvent?>(null)
    val event: StateFlow<UiEvent?> = _event.asStateFlow()

    init {
        Timber.d("%s items loaded: count=%d", label, _items.value.size)
    }

    fun onDetailClick(cardId: Int) {
        Timber.d("%s detail clicked: id=%d", label, cardId)
        _event.value = UiEvent.OpenDetail(cardId)
    }

    fun onUrlClick(url: String) {
        Timber.d("%s url clicked: %s", label, url)
        _event.value = UiEvent.OpenUrl(url)
    }

    fun clearEvent() {
        _event.value = null
    }

    fun getLabel(): String = label
}
