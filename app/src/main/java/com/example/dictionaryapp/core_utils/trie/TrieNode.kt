package com.example.dictionaryapp.core_utils.trie

import com.example.dictionaryapp.app_features.domain.model.WordInfo

class TrieNode<Key>(var key: Key?, var parent: TrieNode<Key>?) {
    val children: HashMap<Key, TrieNode<Key>> = HashMap()
    var isValidWord = false
    var wordInfo: WordInfo? = null
}