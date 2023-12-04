package com.example.dictionaryapp.app_features.data.repository

import com.example.dictionaryapp.app_features.data.local.WordInfoDatabase
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.domain.repository.Repository
import com.example.dictionaryapp.core_utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RepositoryImpl(
    private val api: DictionaryApi,
    private val db: WordInfoDatabase
) : Repository {
    override suspend fun getWordInfoLikeFromWordTable(query: String): Flow<Resource<List<WordInfo>>> =
        flow {
            emit(Resource.Loading())

            // Kiểm tra xem có dữ liệu local hay không
            val localWordInfos = db.wordDao.getWordInfoLike(word = query).map { it.toWordInfo() }
            if (localWordInfos.isNotEmpty()) {
                emit(Resource.Success(localWordInfos))
                return@flow
            }

            try {
                // Lấy dữ liệu từ xa
                val remoteWordInfos = api.getWordInfo(word = query).map{ it.toWordInfoEntity()}

                // Xóa dữ liệu cũ và chèn dữ liệu mới vào local
                db.wordDao.deleteWordInfo(remoteWordInfos.map { it.word })
                db.wordDao.insertWords(remoteWordInfos)

                // Lấy dữ liệu mới từ local
                val newWordInfos = db.wordDao.getWordInfoLike(word = query).map { it.toWordInfo() }
                emit(Resource.Success(newWordInfos))

            } catch (e: HttpException) {
                emit(Resource.Error(
                    message = "Oops, something went wrong!",
                    data = emptyList()
                ))
            } catch (e: IOException) {
                emit(Resource.Error(
                    message = "Couldn't reach the server, check your internet connection.",
                    data = emptyList()
                ))
            }
        }

    override suspend fun getSingleWordInfoFromWordTable(query: String): Flow<Resource<WordInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.wordDao.getSingleWordInfo(word = query).toWordInfo()
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun getAllWordFromWordTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.wordDao.getAllWord().map { it.toWordInfo() }
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun insertWordToWordTable(word: WordInfoEntity): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.wordDao.insertWord(word)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun insertWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.wordDao.insertWords(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun deleteWordFromWordTable(words: List<String>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.wordDao.deleteWordInfo(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun updateWordsToWordTable(words: List<WordInfoEntity>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.wordDao.updateWords(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun fetchRandomUnusedWordsFromWordTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.wordDao.fetchRandomUnusedWords().map { it.toWordInfo() }
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun getWordLikeFromHistoryTable(query: String): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.historyDao.getAllWordLike(word = query).map { it.toWordInfo() }
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun getAllWordFromHistoryTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.historyDao.getAllWord().map { it.toWordInfo() }
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }
        }
    }

    override suspend fun getSingleWordInfoFromHistoryTable(query: String): Flow<Resource<WordInfo>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.historyDao.getSingleWordInfo(word = query).toWordInfo()
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

    override suspend fun insertWordToHistoryTable(word: HistoryEntity): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.historyDao.insertWord(word)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

    override suspend fun insertWordsToHistoryTable(words: List<HistoryEntity>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.historyDao.insertWords(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

    override suspend fun deleteWordFromHistoryTable(words: List<String>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.historyDao.deleteWordInfo(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

    override suspend fun updateWordsToHistoryTable(words: List<HistoryEntity>): Flow<Resource<String>> {
        return flow {
            emit(Resource.Loading())
            try {
                db.historyDao.updateWords(words)
                emit(Resource.Success("Success"))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

    override suspend fun getWordSkippedFromHistoryTable(): Flow<Resource<List<WordInfo>>> {
        return flow {
            emit(Resource.Loading())
            try {
                val word = db.historyDao.getWordSkipped().map { it.toWordInfo() }
                emit(Resource.Success(word))
            } catch (e: Exception) {
                emit(
                    Resource.Error(
                        message = "Oops, something went wrong!",
                        data = null
                    )
                )
            }

        }
    }

}