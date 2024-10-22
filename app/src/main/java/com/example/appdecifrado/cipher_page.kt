package com.example.appdecifrado

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class cipher_page : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cipher_page)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var returnBtn = findViewById<Button>(R.id.returnBtn)
        var cipheredText = intent.getStringExtra("CIPHERED_TEXT")
        var decipheredText = intent.getStringExtra("DECIPHERED_TEXT")
        var textResult = findViewById<TextView>(R.id.resultCipherText)

        textResult.text = if (intent.hasExtra("CIPHERED_TEXT")) cipheredText else decipheredText

        returnBtn.setOnClickListener{
            finish()
        }
    }
}

