package com.example.modul2_xml

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.materialswitch.MaterialSwitch
import com.google.android.material.textfield.TextInputEditText
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var editAmount: TextInputEditText
    private lateinit var spinnerTip: AutoCompleteTextView
    private lateinit var switchRoundUp: MaterialSwitch
    private lateinit var textTipAmount: TextView

    private val tipOptions = listOf("15%", "18%", "20%")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editAmount = findViewById(R.id.editAmount)
        spinnerTip = findViewById(R.id.spinnerTip)
        switchRoundUp = findViewById(R.id.switchRoundUp)
        textTipAmount = findViewById(R.id.textTipAmount)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tipOptions)
        spinnerTip.setAdapter(adapter)

        spinnerTip.setText(tipOptions[0], false)

        editAmount.addTextChangedListener(textWatcher)

        spinnerTip.setOnItemClickListener { _, _, _, _ ->
            calculateTip()
        }

        switchRoundUp.setOnCheckedChangeListener { _, _ ->
            calculateTip()
        }
    }

    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            calculateTip()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun calculateTip() {
        val billText = editAmount.text.toString()

        if (billText.isEmpty()) {
            textTipAmount.text = "$0.00"
            return
        }

        val bill = billText.toDoubleOrNull() ?: 0.0

        val percent = when (spinnerTip.text.toString()) {
            "15%" -> 0.15
            "18%" -> 0.18
            "20%" -> 0.20
            else -> 0.15
        }

        var tip = bill * percent

        if (switchRoundUp.isChecked) {
            tip = ceil(tip)
        }

        textTipAmount.text = "$%.2f".format(tip)
    }
}