package com.example.dictionaryapp.app_features.domain.model

import com.example.dictionaryapp.app_features.utils.DismissDuration

data class WordInfo(
    val word: String? = "",
    val meanings: List<Meaning>,
    val phonetic: String? = "",
    val origin: String? = "",
    val isUsed: Boolean? = false, //kiểm tra đã được dùng làm quiz hay chưa
    val dismissDuration: DismissDuration? = null,
    val skippedTimes: Int? = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ điều chỉnh opts chọn time skip
    val illustration: String? = "" //url ảnh
)