package com.example.dictionaryapp.core_utils.download_words

interface DownloadWords {
    suspend fun downloadWordsToLocal()
    fun readWordsFromAssets(): List<String>
}