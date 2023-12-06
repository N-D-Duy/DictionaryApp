package com.example.dictionaryapp.app_features.presentation.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.main.MainViewModel

class WordDetailActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()
    private var word: WordInfo? = null

    init {
        word = collectSharedData()
    }

    private fun collectSharedData(): WordInfo? {
        var word: WordInfo? = null
        mainViewModel.sharedWord.observe(this) {
            word = it
        }
        return word
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_detail)
        if (word != null) {
            {
                updateUI()
            }
        }
    }

    private fun updateUI() {

    }
}
