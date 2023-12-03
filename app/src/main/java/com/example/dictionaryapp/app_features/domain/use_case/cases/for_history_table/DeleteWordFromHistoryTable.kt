package com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table

import com.example.dictionaryapp.app_features.domain.model.InvalidWordException
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteWordFromHistoryTable(
    private val repository: Repository
) {
    suspend operator fun invoke(words: List<String>): Flow<Resource<String>> {
        if(words.isEmpty()){
            return flow{
                throw InvalidWordException("list of words is empty")
            }
        }
        return repository.deleteWordFromHistoryTable(words)
    }
}