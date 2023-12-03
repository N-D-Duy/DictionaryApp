package com.example.dictionaryapp.app_features.data.local.entity.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<WordInfoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word: WordInfoEntity)

    //delete
    @Query("DELETE FROM `word-table` WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)

    //get
    @Query("SELECT * FROM `word-table` WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfoLike(word: String): List<WordInfoEntity>
    @Query("SELECT * FROM `word-table` WHERE word like :word")
    suspend fun getSingleWordInfo(word: String): WordInfoEntity
    @Query("SELECT * FROM `word-table`")
    suspend fun getAllWord(): List<WordInfoEntity>

    @Query("SELECT * FROM `word-table` WHERE isUsed = 0 ORDER BY RANDOM() LIMIT 20")
    suspend fun fetchRandomUnusedWords(): List<WordInfoEntity>



    //update
    @Update
    suspend fun updateWords(words: List<WordInfoEntity>)

}