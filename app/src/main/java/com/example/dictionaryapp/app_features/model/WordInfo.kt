package com.example.dictionaryapp.app_features.model

data class WordInfo(
    val word: String? = "",
    val meanings: List<Meaning>,
    val phonetic: String? = "",
    val origin: String? = "",
    val dismissDuration: Long? = 0L,
    val skippedTimes: Int? = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ tăng thời gian
    val illustration: String? = "" //url ảnh
)