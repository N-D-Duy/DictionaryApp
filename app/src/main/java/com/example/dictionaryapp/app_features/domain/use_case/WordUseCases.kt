package com.example.dictionaryapp.app_features.domain.use_case

import com.example.dictionaryapp.app_features.domain.use_case.cases.GetWordHistory
import com.example.dictionaryapp.app_features.domain.use_case.cases.GetWordInfo
import com.example.dictionaryapp.app_features.domain.use_case.cases.InsertHistoryWord
import com.example.dictionaryapp.app_features.domain.use_case.cases.InsertWord

data class WordUseCases(
    val getWordInfo: GetWordInfo,
    val getWordHistory: GetWordHistory,
    val insertHistoryWord: InsertHistoryWord,
    val insertWord: InsertWord
)
