package com.example.dictionaryapp.app_features.domain.use_case

import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.DeleteWordFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetAllWordFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetSingleWordInfoFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetWordLikeFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.GetWordSkippedFromHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.InsertWordToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.InsertWordsToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_history_table.UpdateWordsToHistoryTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.DeleteWordFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetAllWordsFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetSingleWordFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetWordsLikeFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.InsertSingleWordToWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.InsertWordsToWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.UpdateWordFromWordTable
import com.example.dictionaryapp.app_features.domain.use_case.cases.for_word_table.GetRandomUnusedWordsFromWordTable

data class WordUseCases(
    //word table
    val getWordInfoLikeFromWordTable: GetWordsLikeFromWordTable,
    val getSingleWordInfoFromWordTable: GetSingleWordFromWordTable,
    val getAllWordFromWordTable: GetAllWordsFromWordTable,
    val insertWordToWordTable: InsertSingleWordToWordTable,
    val insertWordsToWordTable: InsertWordsToWordTable,
    val deleteWordFromWordTable: DeleteWordFromWordTable,
    val updateWordsToWordTable: UpdateWordFromWordTable,
    val fetchRandomUnusedWordsFromWordTable: GetRandomUnusedWordsFromWordTable,


    //history table
    val getAllWordFromHistoryTable: GetAllWordFromHistoryTable,
    val getSingleWordInfoFromHistoryTable: GetSingleWordInfoFromHistoryTable,
    val getWordLikeFromHistoryTable: GetWordLikeFromHistoryTable,
    val insertWordToHistoryTable: InsertWordToHistoryTable,
    val insertWordsToHistoryTable: InsertWordsToHistoryTable,
    val deleteWordFromHistoryTable: DeleteWordFromHistoryTable,
    val updateWordsToHistoryTable: UpdateWordsToHistoryTable,
    val getWordSkippedFromHistoryTable: GetWordSkippedFromHistoryTable

)
