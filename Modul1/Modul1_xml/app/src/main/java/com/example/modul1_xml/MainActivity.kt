package com.example.modul1_xml

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageViewDice1: ImageView = findViewById(R.id.imageViewDice1)
        val imageViewDice2: ImageView = findViewById(R.id.imageViewDice2)
        val buttonRoll: Button = findViewById(R.id.buttonRoll)

        buttonRoll.setOnClickListener {
            val dice1 = (1..6).random()
            val dice2 = (1..6).random()

            imageViewDice1.setImageResource(getDiceResource(dice1))
            imageViewDice2.setImageResource(getDiceResource(dice2))

            var output = "Anda belum beruntung!"
            if (dice1 == dice2) {
                output = "Selamat, anda dapat dadu double"
            }
            Toast.makeText(this, output, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDiceResource(roll: Int): Int {
        return when (roll) {
            0 -> R.drawable.dice_0
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> R.drawable.dice_6
        }
    }
}