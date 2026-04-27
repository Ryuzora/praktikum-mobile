package com.example.modul3xml

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3xml.databinding.ActivityMainBinding
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private companion object {
        const val KEY_MAIN_SCROLL_Y = "key_main_scroll_y"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = CardAdapter(
            data = CardData,
            onDetailClick = { cardId ->
                val intent = Intent(this, DetailActivity::class.java).apply {
                    putExtra("EXTRA_CARD_ID", cardId)
                }
                startActivity(intent)
            },
            onUrlClick = { url ->
                val browserIntent = Intent(Intent.ACTION_VIEW, url.toUri())
                startActivity(browserIntent)
            }
        )

        binding.rvFeatured.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvAllContent.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvFeatured.adapter = adapter
        binding.rvAllContent.adapter = adapter

        val savedScrollY = savedInstanceState?.getInt(KEY_MAIN_SCROLL_Y, 0) ?: 0
        if (savedScrollY > 0) {
            binding.nsvMain.post {
                binding.nsvMain.scrollTo(0, savedScrollY)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_MAIN_SCROLL_Y, binding.nsvMain.scrollY)
        super.onSaveInstanceState(outState)
    }
}