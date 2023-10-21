package com.example.dictionaryapp.app_features.domain.model

data class Definition(
    val antonyms: List<String>, //trái nghĩa
    val definition: String, //định nghĩa của từ
    val example: String?, //some ví dụ
    val synonyms: List<String> //đồng nghĩa
)