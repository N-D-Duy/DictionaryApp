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

    //insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<HistoryEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWord(word: HistoryEntity)

    //delete
    @Query("DELETE FROM `history-table` WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)


    //get
    @Query("SELECT * FROM `history-table` WHERE word LIKE '%' || :word || '%'")
    suspend fun getAllWordLike(word: String): List<HistoryEntity>
    @Query("SELECT * FROM `history-table` WHERE word like :word")
    suspend fun getSingleWordInfo(word: String): HistoryEntity
    @Query("SELECT * FROM `history-table`")
    suspend fun getAllWord(): List<HistoryEntity>


    //update
    @Update
    suspend fun updateWords(words: List<HistoryEntity>)
    @Query("SELECT * FROM `history-table` WHERE isSkipped")
    suspend fun getWordSkipped(): List<HistoryEntity>

}