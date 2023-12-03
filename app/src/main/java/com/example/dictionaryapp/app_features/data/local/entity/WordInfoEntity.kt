package com.example.dictionaryapp.app_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.utils.DismissDuration

@Entity(tableName = "word-table")
data class WordInfoEntity(
    var word: String,
    var phonetic: String,
    var origin: String,
    var meanings: List<Meaning>,
    var isUsed: Boolean? = false,
    var dismissDuration: DismissDuration? = null,
    var isSkipped: Byte? = 0,
    var illustration: String? = "",
    @PrimaryKey val id: Int
) {
    fun toWordInfo(): WordInfo {
        return WordInfo(
            meanings = meanings,
            word = word, 
            phonetic = phonetic,
            origin = origin,
            isUsed = isUsed,
            dismissDuration = dismissDuration,
            isSkipped = isSkipped,
            illustration = illustration
        )
    }

    fun toHistory(): HistoryEntity{
        return HistoryEntity(
            id = id,
            word = word,
            meaning = meanings,
            isSkipped = isSkipped,
            phonetic = phonetic
        )
    }
}