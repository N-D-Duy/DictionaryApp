package com.example.dictionaryapp.app_features.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity

@Dao
interface HistoryDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: HistoryEntity)

    @Query("DELETE FROM `history-table` WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)

    @Query("SELECT * FROM `history-table` WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfo(word: String): HistoryEntity

    @Update
    fun updateWords(words: List<HistoryEntity>)

    @Query("SELECT * FROM `history-table`")
    fun getAllWord(): List<HistoryEntity>

    @Query("SELECT * FROM `history-table` WHERE isSkipped")
    fun getWordSkipped(): List<HistoryEntity>
}