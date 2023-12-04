package com.example.dictionaryapp.app_features.domain.model

data class Meaning(
    val definitions: List<Definition>,
    val partOfSpeech: String? = ""
    //smt else here
)