package com.example.dictionaryapp.app_features.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.model.Definition
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.presentation.state.WordState
import com.example.dictionaryapp.app_features.utils.DismissDuration
import com.example.dictionaryapp.core_utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCases: WordUseCases
) : ViewModel() {

    private var _singleWordState = MutableStateFlow(WordState.SingleWordState())
    val singleWordState = _singleWordState.asStateFlow()

    private var _multipleWordsState = MutableStateFlow(WordState.MultipleWordsState())
    var multipleWordsState = _multipleWordsState.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null

    fun getWordInfoLike(query: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.getWordInfoLikeFromWordTable(query).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = true
                        )
                        //loading processing
                    }

                    is Resource.Error -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

     fun getWordInfo(query: String){
         job?.cancel()
         job = viewModelScope.launch(Dispatchers.IO){
             useCases.getSingleWordInfoFromWordTable(query).collectLatest { result->
                 when(result){
                     is Resource.Loading ->{
                         _singleWordState.value = singleWordState.value.copy(
                             wordInfo = result.data,
                             isLoading = true
                         )
                         //loading
                     }
                     is Resource.Error ->{
                         _singleWordState.value = singleWordState.value.copy(
                             wordInfo = result.data,
                             isLoading = false
                         )
                         _eventFlow.emit(UIEvent.ShowSnackBar(
                             result.message ?: "Unknown error"
                         ))
                     }
                     is Resource.Success ->{
                         _singleWordState.value = singleWordState.value.copy(
                             wordInfo = result.data,
                             isLoading = false
                         )
                     }
                 }
             }
         }
     }
    fun getAllWord() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllWordFromWordTable.invoke().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = true
                        )
                        //loading processing
                    }

                    is Resource.Error -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun insertWord(word: WordInfoEntity) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.insertWordToWordTable.invoke(word).collectLatest {
                //do something after insert
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Insert word", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("Insert word", "Error: ${it.message}")
                    }

                    is Resource.Success -> {
                        Log.d("Insert word", it.data.toString())
                    }
                }
            }
        }
    }

    fun insertWords(words: List<WordInfoEntity>) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.insertWordsToWordTable.invoke(words).collectLatest {
                //do something after insert
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Insert word", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("Insert word", "Error: ${it.message}")
                    }

                    is Resource.Success -> {
                        Log.d("Insert word", it.data.toString())
                    }
                }
            }
        }
    }

    fun deleteWords(words: List<String>) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.deleteWordFromWordTable.invoke(words).collectLatest {
                //do something after delete
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Delete word", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("Delete word", "Error: ${it.message}")
                    }

                    is Resource.Success -> {
                        Log.d("Delete word", it.data.toString())
                    }
                }
            }
        }
    }

    fun updateWord(word: WordInfoEntity) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
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

    fun fetchRandomUnusedWords() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.fetchRandomUnusedWordsFromWordTable.invoke().collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = true
                        )
                        //loading processing
                    }

                    is Resource.Error -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _multipleWordsState.value = multipleWordsState.value.copy(
                            wordList = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }

    }
}

sealed class UIEvent {
    data class ShowSnackBar(val message: String) : UIEvent()
}