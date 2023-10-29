package com.example.dictionaryapp.app_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo

@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: List<Meaning>,
    val dismissDuration: Long? = 0L,
    val skippedTimes: Int? = 0,
    val illustration: String? = "",
    @PrimaryKey val id: Int? = null
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word, 
            phonetic = phonetic,
            origin = origin,
            dismissDuration = dismissDuration,
            skippedTimes = skippedTimes,
            illustration = illustration
        )
    }
}