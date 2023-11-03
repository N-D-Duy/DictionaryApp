package com.example.dictionaryapp

import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel@Inject constructor(
    private val useCases: WordUseCases
) : ViewModel(){
    var currentWordIndex: Int = 0
}