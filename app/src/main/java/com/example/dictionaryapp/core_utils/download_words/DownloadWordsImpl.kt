package com.example.dictionaryapp.core_utils.download_words

import android.content.Context
import android.util.Log
import com.example.dictionaryapp.app_features.data.remote.DictionaryApi
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.core_utils.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DownloadWordsImpl(
    private val useCases: WordUseCases,
    private val context: Context,
    private val api: DictionaryApi
) : DownloadWords {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    override suspend fun downloadWordsToLocal() {
        val words = readWordsFromAssets()
        coroutineScope.launch {
            val deferredResults = words.map { word ->
                async {
                    try {
                        val wordInfo = api.getWordInfo(word).first().toWordInfoEntity()
                        useCases.insertWordToWordTable(wordInfo).collectLatest { resource ->
                            // Xử lý kết quả chèn từ vào cơ sở dữ liệu local
                            when (resource) {
                                is Resource.Success -> {
                                    // Xử lý thành công
                                    Log.d(
                                        "DownloadWordsImpl",
                                        "downloadWordsToLocal: ${resource.data}"
                                    )
                                }

                                is Resource.Error -> {
                                    // Xử lý lỗi khi chèn từ vào cơ sở dữ liệu local
                                    Log.d(
                                        "DownloadWordsImpl",
                                        "downloadWordsToLocal: ${resource.message}"
                                    )
                                }

                                is Resource.Loading -> {
                                    // Đang chờ xử lý
                                    Log.d("DownloadWordsImpl", "downloadWordsToLocal: Loading...")
                                }
                            }
                        }
                    } catch (e: Exception) {
                        Log.e("DownloadWordsImpl", "Error downloading word $word: ${e.message}")
                    }
                    delay(1000)
                }
            }
            deferredResults.awaitAll()
        }
    }

    override fun readWordsFromAssets(): List<String> {
        val words = mutableListOf<String>()
        try {
            // Mở AssetManager
            val assetManager = context.assets

            // Mở tệp tin từ thư mục assets
            val inputStream = assetManager.open("words.txt")

            // Đọc nội dung từ InputStream
            val reader = inputStream.bufferedReader()
            reader.useLines { lines ->
                words.addAll(lines.map { it.trim() })
            }
            // Đóng InputStream
            inputStream.close()
        } catch (e: Exception) {
            // Xử lý lỗi khi đọc từ assets
        }
        return words
    }
}