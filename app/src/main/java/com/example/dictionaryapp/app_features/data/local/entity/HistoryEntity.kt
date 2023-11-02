package com.example.dictionaryapp.app_features.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.app_features.domain.model.Meaning

@Entity(tableName = "history-table")
data class HistoryEntity(
    @PrimaryKey
    val id: String,
    var word:String? = "",
    var meaning: List<Meaning>? = arrayListOf(),
    var isSkipped: Byte? = 0,
    var phonetic:String? = ""
)