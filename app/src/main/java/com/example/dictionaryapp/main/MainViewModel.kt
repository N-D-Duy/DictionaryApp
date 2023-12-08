package com.example.dictionaryapp.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.presentation.state.WordState
import com.example.dictionaryapp.core_utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: WordUseCases
) : ViewModel() {
    var currentWordIndex: Int = 0
    private val _listWord = MutableStateFlow(WordState.MultipleWordsState())
    var listWord = _listWord.asStateFlow()

    private var job: Job? = null

    fun fetchRandomUnusedWords() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.fetchRandomUnusedWordsFromWordTable.invoke().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _listWord.value = listWord.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = true
                        )
                        //loading processing
                    }

                    is Resource.Error -> {
                        _listWord.value = listWord.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }

                    is Resource.Success -> {
                        _listWord.value = listWord.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}
