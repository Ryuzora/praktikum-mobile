package com.example.modul3xml

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.modul3xml.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val cardId = intent.getIntExtra("EXTRA_CARD_ID", -1)

        val card = CardData.find { it.id == cardId }

        if (card == null) {
            Toast.makeText(this, "Data not found", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        binding.ivDetailImage.setImageResource(card.image)
        binding.tvDetailTitle.text = card.title
        binding.tvDetailDescription.text = card.description
        binding.tvDetailContent.text = card.content
    }
}