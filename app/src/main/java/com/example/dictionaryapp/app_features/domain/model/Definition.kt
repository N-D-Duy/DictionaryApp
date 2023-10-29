package com.example.dictionaryapp.app_features.domain.model

data class Definition(
    val antonyms: List<String>? = arrayListOf(), //trái nghĩa
    val definition: String? = "", //định nghĩa của từ
    val example: String? = "", //some ví dụ
    val synonyms: List<String>? = arrayListOf() //đồng nghĩa
)