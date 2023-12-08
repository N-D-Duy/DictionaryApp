package com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table

import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateWordFromWordTable(
    private val repository: Repository
) {
    suspend operator fun invoke(word: WordInfoEntity): Flow<Resource<String>> {
        if (word.word.isEmpty()) {
            return flow {
                throw Exception("this field can't be empty")
            }
        }
        return repository.updateWordToWordTable(word)
    }
}