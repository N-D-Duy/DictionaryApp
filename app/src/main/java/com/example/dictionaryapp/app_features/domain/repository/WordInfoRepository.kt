package com.example.dictionaryapp.app_features.domain.repository

import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>

    fun getHistoryWord(word: String): Flow<Resource<List<WordInfo>>>
    fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>>

    fun insertWordToHistoryTable(word: HistoryEntity): Flow<Resource<String>>
}