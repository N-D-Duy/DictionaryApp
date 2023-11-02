package com.example.dictionaryapp.app_features.utils.optionsupport

class ConvertTime(minutes: Long) {
    val convertMinutesToTime: ()->String =  {
        if (minutes < 60) {
            "$minutes phút"
        } else if (minutes < 1440) {  // 1440 minutes = 1 day
            val hours = minutes / 60
            val remainingMinutes = minutes % 60
            "$hours giờ ${if (remainingMinutes > 0) "$remainingMinutes phút" else ""}"
        } else if (minutes < 43200) {  // 43200 minutes = 1 month
            val days = minutes / 1440
            val remainingHours = (minutes % 1440) / 60
            "$days ngày ${if (remainingHours > 0) "$remainingHours giờ" else ""}"
        } else if(minutes < 525600){
            val month = minutes / 1440 / 30
            val remainingDays = (minutes % 1440) / 30
            "$month tháng ${if (remainingDays > 0) "$remainingDays ngày" else ""}"
        } else{
            val years = minutes / 525600
            "$years năm"
        }
    }
}