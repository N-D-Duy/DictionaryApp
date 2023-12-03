package com.example.dictionaryapp.app_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.model.WordInfo

@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var word:String,
    var meaning: List<Meaning>,
    var isSkipped: Byte? = 0,
    var phonetic:String? = ""
){
    fun toWordInfo(): WordInfo{
        return WordInfo(
            word = word,
            meanings = meaning,
            isSkipped = isSkipped,
            phonetic = phonetic
        )
    }
}