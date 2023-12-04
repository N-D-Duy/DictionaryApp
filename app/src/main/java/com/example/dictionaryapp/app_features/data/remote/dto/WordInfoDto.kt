package com.example.dictionaryapp.app_features.data.remote.dto

import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import kotlin.random.Random

data class WordInfoDto(
    val meanings: List<MeaningDto>,
    val origin: String? = "",
    val phonetic: String? = "",
    val phonetics: List<PhoneticDto>,
    val word: String
) {
    fun toWordInfoEntity(): WordInfoEntity {
        return WordInfoEntity(
            meanings = meanings.map { it.toMeaning() },
            origin = origin,
            phonetic = phonetic,
            word = word,
            id = 0
        )
    }
}