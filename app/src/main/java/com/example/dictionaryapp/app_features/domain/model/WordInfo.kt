package com.example.dictionaryapp.app_features.domain.model

import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.data.local.entity.WordInfoEntity
import com.example.dictionaryapp.app_features.utils.DismissDuration

data class WordInfo(
    var word: String,
    var meanings: List<Meaning>,
    var phonetic: String? = "",
    var origin: String? = "",
    var isHistory: Boolean = false, //check xem đã là từ lịch sử search hay chưa.
    var dismissDuration: DismissDuration? = null,
    var expiredTime: Long? = null, //thời gian hết hạn
    var isSkipped: Byte = 0, //biến kiểm tra số lần người dùng skip theo đó sẽ điều chỉnh opts chọn time skip
    var illustration: String? = "" //url ảnh
) {
    fun toWordEntity(): WordInfoEntity? {
        return WordInfoEntity(
            id = 0,
            word = this.word,
            meanings = this.meanings,
            phonetic = this.phonetic,
            origin = this.origin,
            isHistory = this.isHistory,
            dismissDuration = this.dismissDuration,
            expiredTime = this.expiredTime,
            isSkipped = this.isSkipped,
            illustration = this.illustration
        )
    }

    fun toHistoryEntity(): HistoryEntity? {
        return HistoryEntity(
            id = 0,
            word = this.word,
            meaning = this.meanings,
            phonetic = this.phonetic,
            isSkipped = this.isSkipped,
            dismissDuration = this.dismissDuration,
            expiredTime = this.expiredTime
        )
    }
}

class InvalidWordException(message: String) : Exception(message)