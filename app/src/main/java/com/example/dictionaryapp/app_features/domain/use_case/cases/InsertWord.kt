package com.example.dictionaryapp.app_features.domain.use_case.cases

import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.domain.model.InvalidWordException
import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository

class InsertWord(
    private val repository: WordInfoRepository
) {
    @Throws(InvalidWordException::class)
    operator fun invoke(word: WordInfoEntity){
        if(word.word.isBlank()){
            throw InvalidWordException("This word field can't be empty")
        }
        if (word.meanings.isEmpty()){
            throw InvalidWordException("This meanings filed can't be empty")
        }
        repository.insertWordToWordTable(word)
    }
}