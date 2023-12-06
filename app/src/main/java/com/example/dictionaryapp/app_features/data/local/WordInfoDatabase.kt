package com.example.dictionaryapp.app_features.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.data.local.entity.dao.HistoryDao
import com.example.dictionaryapp.app_features.data.local.entity.dao.WordInfoDao

@Database(
    entities = [WordInfoEntity::class, HistoryEntity::class],
    version = 6,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class WordInfoDatabase: RoomDatabase() {
    abstract val wordDao: WordInfoDao
    abstract val historyDao: HistoryDao
}