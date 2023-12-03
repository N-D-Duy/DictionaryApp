package com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table

import android.util.Log
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetSingleWordFromWordTable(
    private val repository: Repository
) {
    suspend operator fun invoke(query: String): Flow<Resource<WordInfo>> {
        if(query.isBlank()) {
            return flow {
                Log.e("GetSingleWordFromWordTable", "invoke: query is blank")
            }
        }
        return repository.getSingleWordInfoFromWordTable(query)
    }
}