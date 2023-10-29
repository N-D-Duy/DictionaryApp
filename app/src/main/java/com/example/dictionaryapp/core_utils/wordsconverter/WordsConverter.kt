package com.example.dictionaryapp.core_utils.wordsconverter

import android.text.SpannableString

interface WordsConverter {
    fun convertAndColor(word: String): Pair<SpannableString, List<Int>>
    fun vowelToUnderscore(word: String): Pair<String, List<Int>>
    fun colorUnderscores(text: String, indices: List<Int>): SpannableString
    fun revealHiddenChars(spannable: SpannableString, originalWord: String, indices: List<Int>): SpannableString
}