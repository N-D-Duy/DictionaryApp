package com.example.dictionaryapp.app_features.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.app_features.domain.model.Definition
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.utils.DismissDuration
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val useCases: WordUseCases
): ViewModel() {

    private val _text = MutableLiveData<List<WordInfo>>().apply {
        val def1 = Definition(definition = "Tội Phạm")
        val def2 = Definition(definition = "Sách")
        val def3 = Definition(definition = "Con Ruồi")

        val mean1 = Meaning(arrayListOf(def1))
        val mean2 = Meaning(arrayListOf(def2))
        val mean3 = Meaning(arrayListOf(def3))

        val word1 = WordInfo(word = "Crime", meanings = arrayListOf(mean1), dismissDuration = DismissDuration.FIVE_MINUTES)
        val word2 = WordInfo(word = "Book", meanings = arrayListOf(mean2), dismissDuration = DismissDuration.ONE_MINUTE)
        val word3 = WordInfo(word = "FlyCar", meanings = arrayListOf(mean3), dismissDuration = DismissDuration.THIRTY_MINUTES)
        value = arrayListOf(word1, word2, word3)
    }
    val text: LiveData<List<WordInfo>> = _text


}