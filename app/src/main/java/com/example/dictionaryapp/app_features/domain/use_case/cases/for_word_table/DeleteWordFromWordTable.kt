package com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table

import com.example.dictionaryapp.app_features.domain.model.InvalidWordException
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteWordFromWordTable(
    private val repository: Repository
) {
    suspend operator fun invoke(words: List<String>): Flow<Resource<String>> {
        if(words.isEmpty()){
            return flow{
                throw InvalidWordException("Word must not be empty")
            }
        }
        return repository.deleteWordFromWordTable(words)
    }
}