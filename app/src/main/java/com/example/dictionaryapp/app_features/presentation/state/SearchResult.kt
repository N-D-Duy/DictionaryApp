package com.example.dictionaryapp.app_features.presentation.state

import com.example.dictionaryapp.app_features.domain.model.WordInfo

data class SearchResult(
    val listWord: List<WordInfo>? = null,
    val isLoading: Boolean = false,
)