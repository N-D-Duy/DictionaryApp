package com.example.dictionaryapp.app_features.presentation.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dictionaryapp.app_features.domain.use_case.GetWordInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class HistoryViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = _text
}