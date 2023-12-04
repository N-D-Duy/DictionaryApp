package com.example.dictionaryapp.app_features.presentation.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dictionaryapp.MainActivity
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
    }
}