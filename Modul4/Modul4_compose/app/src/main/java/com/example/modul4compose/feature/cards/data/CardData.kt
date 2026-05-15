package com.example.modul4compose.feature.cards.data

import com.example.modul4compose.R
import timber.log.Timber

object CardData {
    fun getCards(): List<ContentCard> {
        val cards = listOf(
            ContentCard(
                id = 1,
                title = "Lee Jae In",
                description = "Yooborn Company",
                age = 22,
                image = R.drawable.lee_jae_in,
                link = "https://asianwiki.com/Lee_Jae-In_(2004)",
                content = "Sa mau panggil tapi sa lupa ko punya nama, karena yang di kepala cuma ingat rasa. rasa yang dolo dolo dolo pasa polo polo polo, sakarang su ganti bantal polo. lalu kini cuma bisa da da da da sayange, luka lama so anyor... lapas pigi ding da da da"
            ), ContentCard(
                id = 2,
                title = "Kim Da-Mi",
                description = "Unity Artists Agency",
                age = 31,
                image = R.drawable.kimdami,
                link = "https://asianwiki.com/Kim_Da-Mi",
                content = "Under city stars we spin tonight, hands in rhythm, hearts in light, sing it once and sing it true, every chorus comes back to you."
            ), ContentCard(
                id = 3,
                title = "Roh Yoon-Seo",
                description = "GOTT",
                age = 26,
                image = R.drawable.rohyoonseo,
                link = "https://asianwiki.com/Roh_Yoon-Seo",
                content = "Midnight rain on the rooftop sings, I hum your name between the strings, city lights fade into blue, every little chorus points to you."
            ), ContentCard(
                id = 4,
                title = "IU",
                description = "EDAM Entertainment",
                age = 33,
                image = R.drawable.iu,
                link = "https://asianwiki.com/IU",
                content = "Step by step through neon streets, heartbeat dancing with the beat, when the sunrise paints the sky, we keep this melody alive."
            ), ContentCard(
                id = 5,
                title = "Bae Suzy",
                description = "Management SOOP",
                age = 32,
                image = R.drawable.baesuzy,
                link = "https://asianwiki.com/Bae_Suzy",
                content = "I wrote a chorus on a train, every window spelled your name, soft guitar and summer breeze, turned our silence into harmony."
            ), ContentCard(
                id = 6,
                title = "Roh Jeong-Eui",
                description = "Namoo Actors",
                age = 25,
                image = R.drawable.rohjeongeui,
                link = "https://asianwiki.com/Roh_Jeong-Eui",
                content = "Hold the rhythm, do not let go, moonlight moving soft and slow, even shadows learn to shine, when your voice is next to mine."
            ), ContentCard(
                id = 7,
                title = "Seol In-A",
                description = "GOLDMEDALIST Entertainment",
                age = 30,
                image = R.drawable.seorina,
                link = "https://asianwiki.com/Seol_In-A",
                content = "Final verse and open road, carry love in every note, if tomorrow calls us far, sing and I will find where you are."
            ), ContentCard(
                id = 8,
                title = "Kim Se-Jeong",
                description = "BH Entertainment",
                age = 30,
                image = R.drawable.kimsejeong,
                link = "https://asianwiki.com/Kim_Se-Jeong_(Gugudan)",
                content = "Golden hour in your eyes, quiet fire in the sky, every note you softly sing turns the dark to morning light."
            )
        )
        cards.forEach { card ->
            Timber.i("Card inserted into list: id=%d title=%s", card.id, card.title)
        }
        return cards
    }
}
