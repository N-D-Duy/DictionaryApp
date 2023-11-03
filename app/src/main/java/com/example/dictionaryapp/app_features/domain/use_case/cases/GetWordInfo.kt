package com.example.dictionaryapp.app_features.domain.use_case.cases

import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetWordInfo(
    private val repository: WordInfoRepository
) {
    operator fun invoke(word: String): Flow<Resource<List<WordInfo>>> {
        if(word.isBlank()) {
            return flow {
                //return nothing if word got from repo is blank
            }
        }
        return repository.getWordInfo(word)
    }
}