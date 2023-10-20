package com.example.dictionaryapp.app_features.data.repository

import com.example.dictionaryapp.app_features.data.local.WordInfoDao
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository
import com.example.dictionaryapp.app_features.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

class WordInfoRepositoryImpl (
    private val api: DictionaryApi,
    private val dao: WordInfoDao
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> {
        TODO("Not yet implemented")
    }
}