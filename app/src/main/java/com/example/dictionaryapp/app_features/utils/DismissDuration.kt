package com.example.dictionaryapp.app_features.utils

import java.io.Serializable

enum class DismissDuration(val durationInMinutes: Long): Serializable {
    ONE_MINUTE(1),
    FIVE_MINUTES(5),
    TEN_MINUTES(10),
    THIRTY_MINUTES(30),
    ONE_DAY(1440),
    THREE_DAYS(4320),
    FIVE_DAYS(7200),
    TEN_DAYS(14400),
    ONE_MONTH(43200),
    THREE_MONTHS(129600),
    FIVE_MONTHS(216000),
    ONE_YEAR(525600)
}