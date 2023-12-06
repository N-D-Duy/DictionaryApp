package com.example.dictionaryapp.app_features.utils.upcoming_word

import com.example.dictionaryapp.app_features.domain.model.WordInfo

interface PriorityQueue {
    fun enqueue(element: WordInfo)
    fun dequeue(): WordInfo?
    fun peek(): WordInfo?
    fun isEmpty(): Boolean
}