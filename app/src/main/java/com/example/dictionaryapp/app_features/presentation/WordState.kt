package com.example.dictionaryapp.app_features.presentation

import com.example.dictionaryapp.app_features.domain.model.WordInfo

data class WordState(
    val wordInfo: WordInfo? = null,
    var isLoading: Boolean = false,
    var isContained: Boolean = false
)