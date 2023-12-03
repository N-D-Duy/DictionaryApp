package com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table

import com.example.dictionaryapp.app_features.domain.model.InvalidWordException
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSingleWordInfoFromHistoryTable(
    private val repository: Repository
) {
    suspend operator fun invoke(query: String): Flow<Resource<WordInfo>> {
        if(query.isEmpty()){
            return flow{
                throw InvalidWordException("Query is empty")
            }
        }
        return repository.getSingleWordInfoFromHistoryTable(query)
    }
}