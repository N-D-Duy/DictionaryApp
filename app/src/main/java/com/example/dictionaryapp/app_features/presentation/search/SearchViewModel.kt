package com.example.dictionaryapp.app_features.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject

@HiltViewModel
class SearchViewModel@Inject constructor(
    private val useCases: WordUseCases
): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is search Fragment"
    }
    val text: LiveData<String> = _text

    private var searchJob: Job? = null
    private var prefixMatchJob: Job? = null
}