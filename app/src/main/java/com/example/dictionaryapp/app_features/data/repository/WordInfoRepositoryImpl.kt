package com.example.dictionaryapp.app_features.data.repository

import com.example.dictionaryapp.app_features.data.local.WordInfoDatabase
import com.example.dictionaryapp.app_features.data.local.entity.dao.HistoryDao
import com.example.dictionaryapp.app_features.data.local.entity.dao.WordInfoDao
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

class WordInfoRepositoryImpl (
    private val api: DictionaryApi,
    private val db: WordInfoDatabase
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {
        TODO("Not yet implemented")
    }
}