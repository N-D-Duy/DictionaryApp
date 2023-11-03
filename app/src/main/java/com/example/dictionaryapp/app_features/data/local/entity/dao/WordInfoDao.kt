package com.example.dictionaryapp.app_features.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: WordInfoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordInfoEntity)
    @Query("DELETE FROM `word-table` WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)

    @Query("SELECT * FROM `word-table` WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfo(word: String): List<WordInfoEntity>

    @Query("SELECT * FROM `word-table` WHERE isUsed = 0 ORDER BY RANDOM() LIMIT 20")
    fun fetchRandomUnusedWords(): List<WordInfoEntity>

    @Query("SELECT * FROM `word-table`")
    fun getAllWord(): List<WordInfoEntity>


    @Update
    fun updateWords(words: List<WordInfoEntity>)

}