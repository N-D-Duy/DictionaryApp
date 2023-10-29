package com.example.dictionaryapp.core_utils.wordsconverter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.example.dictionaryapp.R
import kotlin.random.Random

class WordsConverterImpl : WordsConverter{
    companion object {
        private var instance: WordsConverterImpl? = null
        fun getInstance(): WordsConverterImpl {
            if (instance == null) {
                instance = WordsConverterImpl()
            }
            return instance!!
        }
    }

    override fun convertAndColor(word: String): Pair<SpannableString, List<Int>> {
        val (modifiedWord, indices) = vowelToUnderscore(word)
        return Pair(colorUnderscores(modifiedWord, indices), indices)
    }

    // get 2 diff numbers
    private fun generateDistinctRandomIndices(length: Int): List<Int> {
        val index1 = Random.nextInt(length)
        var index2 = Random.nextInt(length)
        while (index2 == index1) {
            index2 = Random.nextInt(length)
        }
        return listOf(index1, index2)
    }

    // Convert word, like "book" -> "b__k", and return the modified word and the indices of replaced characters
    override fun vowelToUnderscore(word: String): Pair<String, List<Int>> {
        val pattern = "[aeiouAEIOU]".toRegex()
        val indices = mutableListOf<Int>()
        var modifiedWord = pattern.replace(word) {
            indices.add(it.range.first)
            "_"
        }

        // Handle words without vowels
        if (indices.isEmpty()) {
            if (word.length > 3) {
                val randomIndices = generateDistinctRandomIndices(word.length)
                modifiedWord = StringBuilder(word).apply {
                    randomIndices.forEach { index ->
                        this[index] = '_'
                        indices.add(index)
                    }
                }.toString()
            } else {
                val randomIndex = Random.nextInt(word.length)
                modifiedWord = word.substring(0, randomIndex) + "_" + word.substring(randomIndex + 1)
                indices.add(randomIndex)
            }
        }

        return Pair(modifiedWord, indices)
    }

    // Color the underscores
    override fun colorUnderscores(text: String, indices: List<Int>): SpannableString {
        val spannable = SpannableString(text)
        for (i in indices) {
            spannable.setSpan(
                ForegroundColorSpan(Color.BLUE),
                i,
                i + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannable
    }


    // Function to reveal the hidden characters but keep them colored
    override fun revealHiddenChars(spannable: SpannableString, originalWord: String, indices: List<Int>): SpannableString {
        // Convert SpannableString to SpannableStringBuilder for mutability (cuz SpannableString doesn't contain replace() method!)
        val spannableBuilder = SpannableStringBuilder(spannable)

        for (i in indices) {
            // Replace the underscore with the original character
            spannableBuilder.replace(i, i + 1, originalWord.substring(i, i + 1))

            // Re-apply the color span
            spannableBuilder.setSpan(
                ForegroundColorSpan(Color.BLUE),
                i,
                i + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        // Convert it back to SpannableString
        return SpannableString.valueOf(spannableBuilder)
    }
}