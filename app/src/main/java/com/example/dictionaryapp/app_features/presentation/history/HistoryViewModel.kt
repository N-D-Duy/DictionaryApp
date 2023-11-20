package com.example.dictionaryapp.app_features.presentation.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.presentation.WordState
import com.example.dictionaryapp.core_utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel@Inject constructor(
    private val useCases: WordUseCases
) : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is history Fragment"
    }
    val text: LiveData<String> = _text

    private var _state = mutableStateOf(WordState())
    val state: MutableState<WordState> = _state
    private var job: Job? = null

    fun getSingleHistoryWord(query: String){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO){
            useCases.getWordHistory(query).onEach { res->
                when(res){
                    is Resource.Loading ->{
                        //loading
                    }
                    is Resource.Error ->{

                    }
                    is Resource.Success ->{
                        _state.value = _state.value.copy(
                            wordInfo = res.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getAllHistory(){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {

        }
    }

    fun insertHistory(historyEntity: HistoryEntity){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.insertHistoryWord.invoke(historyEntity)
        }
    }
}