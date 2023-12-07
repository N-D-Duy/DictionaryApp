package com.example.dictionaryapp.app_features.domain.model

import java.io.Serializable

data class Definition(
    var antonyms: List<String>? = arrayListOf(), //trái nghĩa
    val definition: String? = "", //định nghĩa của từ
    var example: String? = "", //some ví dụ
    var synonyms: List<String>? = arrayListOf() //đồng nghĩa
): Serializable