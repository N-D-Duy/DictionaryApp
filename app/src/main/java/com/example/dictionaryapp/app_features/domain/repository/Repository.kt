package com.example.dictionaryapp.app_features.domain.repository

import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    //for word table
    suspend fun getWordInfoLikeFromWordTable(query: String): Flow<Resource<List<WordInfo>>>
    suspend fun getSingleWordInfoFromWordTable(query: String): Flow<Resource<WordInfo>>
    suspend fun getAllWordFromWordTable(): Flow<Resource<List<WordInfo>>>


    suspend fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>>

    suspend fun insertWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>>

    suspend fun deleteWordFromWordTable(words: List<String>): Flow<Resource<String>>

    suspend fun updateWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>>

    suspend fun fetchRandomUnusedWordsFromWordTable(): Flow<Resource<List<WordInfo>>>

    //for history table
    suspend fun getWordLikeFromHistoryTable(query: String): Flow<Resource<List<WordInfo>>>
    suspend fun getAllWordFromHistoryTable(): Flow<Resource<List<WordInfo>>>
    suspend fun getSingleWordInfoFromHistoryTable(query: String): Flow<Resource<WordInfo>>

    suspend fun insertWordToHistoryTable(word: HistoryEntity): Flow<Resource<String>>
    suspend fun insertWordsToHistoryTable(words: List<HistoryEntity>): Flow<Resource<String>>

    suspend fun deleteWordFromHistoryTable(words: List<String>): Flow<Resource<String>>

    suspend fun updateWordsToHistoryTable(words: List<HistoryEntity>): Flow<Resource<String>>

    suspend fun getWordSkippedFromHistoryTable(): Flow<Resource<List<WordInfo>>>
}