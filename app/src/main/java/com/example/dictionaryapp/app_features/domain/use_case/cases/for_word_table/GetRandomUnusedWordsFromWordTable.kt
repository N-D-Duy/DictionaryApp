package com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table

import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow

class GetRandomUnusedWordsFromWordTable(
    private val repository: Repository
) {
    suspend operator fun invoke(): Flow<Resource<List<WordInfo>>> {
        return repository.fetchRandomUnusedWordsFromWordTable()
    }
}