package com.example.dictionaryapp.app_features.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.presentation.state.SearchResult
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
class SearchViewModel@Inject constructor(
    private val useCases: WordUseCases
): ViewModel() {

    private val _result = MutableStateFlow(SearchResult())
    var result = _result.asStateFlow()

    private var searchJob: Job? = null

    fun searchWord(word: String) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            useCases.getWordInfoLikeFromWordTable(word).collectLatest {state->
                when(state){
                    is Resource.Loading -> {
                        _result.value = result.value.copy(
                            listWord = state.data ?: emptyList(),
                            isLoading = true
                        )
                    }
                    is Resource.Success -> {
                        _result.value = result.value.copy(
                            listWord = state.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error -> {
                        _result.value = result.value.copy(
                            listWord = state.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun updateWord(word: WordInfoEntity) {
        searchJob?.cancel()
        searchJob = viewModelScope.launch(Dispatchers.IO) {
            useCases.updateWordToWordTable.invoke(word).collectLatest {
                //do something after update
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Update word", "Loading")
                    }
                    is Resource.Error -> {
                        Log.d("Update word", "Error: ${it.message}")
                    }

                    is Resource.Success -> {
                        Log.d("Update word", it.data.toString())
                    }
                }
            }
        }
    }
}