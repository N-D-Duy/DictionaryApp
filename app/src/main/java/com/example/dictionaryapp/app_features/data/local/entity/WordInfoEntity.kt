package com.example.dictionaryapp.app_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.utils.DismissDuration

@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: List<Meaning>,
    val isUsed: Boolean? = false,
    val dismissDuration: DismissDuration? = null,
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
            isUsed = isUsed,
            dismissDuration = dismissDuration,
            skippedTimes = skippedTimes,
            illustration = illustration
        )
    }
}