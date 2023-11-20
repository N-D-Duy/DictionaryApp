package com.example.dictionaryapp.app_features.data.repository

import com.example.dictionaryapp.app_features.data.local.WordInfoDatabase
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.WordInfoRepository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WordInfoRepositoryImpl (
    private val api: DictionaryApi,
    private val db: WordInfoDatabase
): WordInfoRepository {
    override fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>> = flow{
        emit(Resource.Loading())
        try{
            val list = db.wordDao.getAllWord().map { it.toWordInfo() }
            emit(Resource.Success(list))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getHistoryWord(word: String): Flow<Resource<WordInfo>> = flow{
        emit(Resource.Loading())
        try{
            val word = db.historyDao.getWordInfo(word = word).toWordInfo()
            emit(Resource.Success(word))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun getAllHistory(): Flow<Resource<List<WordInfo>>> = flow{
        emit(Resource.Loading())
        try{
            val history = db.historyDao.getAllWord().map { it.toWordInfo() }
            emit(Resource.Success(history))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>>  = flow{
        emit(Resource.Loading())
        try{
            db.wordDao.insertWord(word)
            emit(Resource.Success("insert word to word table successful"))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }

    override fun insertWordToHistoryTable(word: HistoryEntity): Flow<Resource<String>> = flow{
        emit(Resource.Loading())
        try{
            db.historyDao.insertWord(word)
            emit(Resource.Success("insert word to history table successful"))
        } catch (e: Exception){
            emit(Resource.Error(e.message.toString()))
        }
    }
}