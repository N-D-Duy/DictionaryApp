package com.example.dictionaryapp.app_features.presentation.history

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.app_features.presentation.state.WordState
import com.example.dictionaryapp.core_utils.Resource
import com.example.dictionaryapp.core_utils.download_words.DownloadWords
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCases: WordUseCases,
    private val downloadWords: DownloadWords
) : ViewModel() {
    private var _singleWordState = MutableStateFlow(WordState.SingleWordState())
    val singleWordState = _singleWordState.asStateFlow()

    private var _multipleWordsState = MutableStateFlow(WordState.MultipleWordsState())
    var multipleWordsState = _multipleWordsState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var job: Job? = null
    fun getHistoryWord(query: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.getSingleWordInfoFromHistoryTable(query).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        _singleWordState.value = singleWordState.value.copy(
                            wordInfo = result.data,
                            isLoading = true
                        )
                        //loading
                    }

                    is Resource.Error -> {
                        _singleWordState.value = singleWordState.value.copy(
                            wordInfo = result.data,
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown error"
                            )
                        )
                    }

                    is Resource.Success -> {
                        _singleWordState.value = singleWordState.value.copy(
                            wordInfo = result.data,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun getAllHistoryWord() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.getAllWordFromHistoryTable.invoke().collectLatest { result ->
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

    fun getWordLikeFromHistoryTable(query: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.getWordLikeFromHistoryTable.invoke(query).collectLatest { result ->
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

    fun insertHistory(historyEntity: HistoryEntity) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            useCases.insertWordToHistoryTable.invoke(historyEntity).collectLatest {
                //do something after insert
                when (it) {
                    is Resource.Loading -> {
                        Log.d("Insert history", "Loading")
                    }

                    is Resource.Error -> {
                        Log.d("Insert history", "Error: ${it.message}")
                    }

                    is Resource.Success -> {
                        Log.d("Insert history", it.data.toString())
                    }
                }
            }
        }

        fun insertHistories(histories: List<HistoryEntity>) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.IO) {
                useCases.insertWordsToHistoryTable.invoke(histories).collectLatest {
                    //do something after insert
                    when (it) {
                        is Resource.Loading -> {
                            Log.d("Insert history", "Loading")
                        }

                        is Resource.Error -> {
                            Log.d("Insert history", "Error: ${it.message}")
                        }

                        is Resource.Success -> {
                            Log.d("Insert history", it.data.toString())
                        }
                    }
                }
            }
        }

        fun deleteHistory(histories: List<String>) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.IO) {
                useCases.deleteWordFromHistoryTable.invoke(histories).collectLatest {
                    //do something after delete
                    when (it) {
                        is Resource.Loading -> {
                            Log.d("Delete history", "Loading")
                        }

                        is Resource.Error -> {
                            Log.d("Delete history", "Error: ${it.message}")
                        }

                        is Resource.Success -> {
                            Log.d("Delete history", it.data.toString())
                        }
                    }
                }
            }
        }

        fun updateHistory(histories: List<HistoryEntity>) {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.IO) {
                useCases.updateWordsToHistoryTable.invoke(histories).collectLatest {
                    //do something after update
                    when (it) {
                        is Resource.Loading -> {
                            Log.d("Update history", "Loading")
                        }

                        is Resource.Error -> {
                            Log.d("Update history", "Error")
                        }

                        is Resource.Success -> {
                            Log.d("Update history", it.data.toString())
                        }
                    }
                }
            }
        }

        fun getSkippedHistory() {
            job?.cancel()
            job = viewModelScope.launch(Dispatchers.IO) {
                useCases.getWordSkippedFromHistoryTable.invoke().collectLatest { result ->
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

    fun downLoadMoreWord(){
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            downloadWords.downloadWordsToLocal()
        }
    }

    sealed class UIEvent {
        data class ShowSnackBar(val message: String) : UIEvent()
    }
}
