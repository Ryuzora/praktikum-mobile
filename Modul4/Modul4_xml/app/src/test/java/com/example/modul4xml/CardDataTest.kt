package com.example.modul4xml

import com.example.modul4xml.data.CardData
import org.junit.Assert.assertTrue
import org.junit.Test

class CardDataTest {

    @Test
    fun cardData_isNotEmpty() {
        assertTrue("CardData should not be empty", CardData.isNotEmpty())
    }
}

