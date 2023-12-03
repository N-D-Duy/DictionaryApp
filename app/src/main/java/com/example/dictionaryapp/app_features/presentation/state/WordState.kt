package com.example.dictionaryapp.app_features.presentation.state

import com.example.dictionaryapp.app_features.domain.model.WordInfo

sealed class WordState {
    data class SingleWordState(var wordInfo: WordInfo? = null, var isLoading: Boolean = false, var isContained: Boolean = false) : WordState()
    data class MultipleWordsState(var wordList: List<WordInfo>? = null, var isLoading: Boolean = false) : WordState()
}