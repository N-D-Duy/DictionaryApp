package com.example.dictionaryapp.app_features.domain.model

import com.example.dictionaryapp.app_features.utils.DismissDuration

data class WordInfo(
    var word: String? = "",
    var meanings: List<Meaning>,
    var phonetic: String? = "",
    var origin: String? = "",
    var isUsed: Boolean? = false, //kiểm tra đã được dùng làm quiz hay chưa
    var dismissDuration: DismissDuration? = null,
    var isSkipped: Byte? = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ điều chỉnh opts chọn time skip
    var illustration: String? = "" //url ảnh
)