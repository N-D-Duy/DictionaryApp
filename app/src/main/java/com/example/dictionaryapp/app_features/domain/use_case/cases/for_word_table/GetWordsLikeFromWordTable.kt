package com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table

import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordsLikeFromWordTable(
    private val repository: Repository
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<WordInfo>>> {
        if (query.isBlank()) {
            return flow {
                //return nothing if word got from repo is blank
            }
        }
        return repository.getWordInfoLikeFromWordTable(query)
    }
}