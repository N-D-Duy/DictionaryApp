package com.example.dictionaryapp.app_features.utils

enum class DismissDuration(val durationInMinutes: Long) {
    TEN_MINUTES(10),
    ONE_DAY(1440),
    THREE_DAYS(4320),
    FIVE_DAYS(7200),
    TEN_DAYS(14400),
    ONE_MONTH(43200),
    THREE_MONTHS(129600),
    FIVE_MONTHS(216000),
    ONE_YEAR(518400)
}