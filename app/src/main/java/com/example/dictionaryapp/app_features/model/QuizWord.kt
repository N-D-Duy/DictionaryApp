package com.example.dictionaryapp.app_features.model

data class QuizWord(
    val word: String? = "",
    val meaning: String? = "",
    val phonetic: String? = "",
    val dismissDuration: Long? = 0L,
    val skippedTimes: Int? = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ tăng thời gian
    val illustration: String? = "" //url anh
)