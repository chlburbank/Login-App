package com.example.appdecifrado

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var spinnerOption = findViewById<Spinner>(R.id.spinnerOption)
        var cipherBtn = findViewById<Button>(R.id.chipherBtn);
        var decipherBtn = findViewById<Button>(R.id.decipherBtn);
        var cipherText = findViewById<EditText>(R.id.textToCipher);
        var shiftLetters = 2

        var options = arrayOf("Ceaser Cipher", "Vigenère Cipher")
        var adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinnerOption.adapter = adapter

        cipherBtn.setOnClickListener{
            var inputText = cipherText.text.toString()
            var selectedItemSpinner = spinnerOption.selectedItem.toString()

            if (selectedItemSpinner == "Ceaser Cipher") {
                var cipheredText = cipherMethod(inputText, shiftLetters)

                var intent = Intent(this, cipher_page::class.java).apply {
                    putExtra("CIPHERED_TEXT", cipheredText)
                }
                startActivity(intent)
            } else {
                var cipheredText = vigenereCipheMethod(inputText, "PAS")

                var intent = Intent(this, cipher_page::class.java).apply {
                    putExtra("CIPHERED_TEXT", cipheredText)
                }
                startActivity(intent)
            }

        }
        decipherBtn.setOnClickListener{
            var inputText = cipherText.text.toString()
            var selectedItemSpinner = spinnerOption.selectedItem.toString()

            if (selectedItemSpinner == "Ceaser Cipher") {
                var cipheredText = decipherMethod(inputText, shiftLetters)

                var intent = Intent(this, cipher_page::class.java).apply {
                    putExtra("DECIPHERED_TEXT", cipheredText)
                }
                startActivity(intent)
            } else {
                var cipheredText = decipherVigenereCipheMethod(inputText, "PAS")

                var intent = Intent(this, cipher_page::class.java).apply {
                    putExtra("DECIPHERED_TEXT", cipheredText)
                }
                startActivity(intent)
            }
        }
    }
    fun cipherMethod(input : String, shiftValue : Int): String {
        var result = StringBuilder();
        for (char in input) {
            when {
                char.isUpperCase() -> {
                    var shiftedLetter = 'A' + (char - 'A' + shiftValue) % 26
                    result.append(shiftedLetter)
                }
                char.isLowerCase() -> {
                    var shiftedLetter = 'a' + (char - 'a' + shiftValue) % 26
                    result.append(shiftedLetter)
                }
                else -> {
                    result.append(char)
                }
            }
        }
        return result.toString()
    }
    fun decipherMethod(input : String, shiftValue : Int): String {
        var result = StringBuilder();
        for (char in input) {
            when {
                char.isUpperCase() -> {
                    var shiftedLetter = 'A' + (char - 'A' - shiftValue) % 26
                    result.append(shiftedLetter)
                }
                char.isLowerCase() -> {
                    var shiftedLetter = 'a' + (char - 'a' - shiftValue) % 26
                    result.append(shiftedLetter)
                }
                else -> {
                    result.append(char)
                }
            }
        }
        return result.toString()
    }
    fun vigenereCipheMethod(input: String, keyword: String): String {
        val result = StringBuilder()
        val keywordLength = keyword.length
        var keywordIndex = 0

        for (char in input) {
            if (char.isLetter()) {
                val shift = keyword[keywordIndex % keywordLength].toUpperCase() - 'A'
                if (char.isUpperCase()) {
                    val shiftedLetter = 'A' + (char - 'A' + shift) % 26
                    result.append(shiftedLetter)
                } else {
                    val shiftedLetter = 'a' + (char - 'a' + shift) % 26
                    result.append(shiftedLetter)
                }
                keywordIndex++
            } else {
                result.append(char) // Non-alphabet characters remain unchanged
            }
        }
        return result.toString()
    }
    fun decipherVigenereCipheMethod(input: String, keyword: String): String {
        val result = StringBuilder()
        val keywordLength = keyword.length
        var keywordIndex = 0

        for (char in input) {
            if (char.isLetter()) {
                val shift = keyword[keywordIndex % keywordLength].toUpperCase() - 'A'
                if (char.isUpperCase()) {
                    val shiftedLetter = 'A' + (char - 'A' - shift + 26 ) % 26
                    result.append(shiftedLetter)
                } else {
                    val shiftedLetter = 'a' + (char - 'a' - shift + 26 ) % 26
                    result.append(shiftedLetter)
                }
                keywordIndex++
            } else {
                result.append(char) // Non-alphabet characters remain unchanged
            }
        }
        return result.toString()
    }
}