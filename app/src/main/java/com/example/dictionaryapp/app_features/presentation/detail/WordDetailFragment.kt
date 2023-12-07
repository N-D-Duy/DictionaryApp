package com.example.dictionaryapp.app_features.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.databinding.FragmentWordDetailBinding

class WordDetailFragment : Fragment() {
    private var _binding: FragmentWordDetailBinding? = null
    private val binding get() = _binding!!
    private val progressBar get() = binding.progressBarWordDetail
    private val wordDetailContainer get() = binding.wordDetailContainer
    private val tvWord get() = binding.textWord
    private val tvPhonetic get() = binding.textPhonetic
    private val tvOrigin get() = binding.textOrigin
    private val rcvMeanings get() = binding.rcvMeanings

    private fun updateUI(word: WordInfo) {
        tvWord.text = word.word
        tvPhonetic.text = word.phonetic
        tvOrigin.text = word.origin
        val adapter = WordDetailAdapter(word)
        rcvMeanings.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentWordDetailBinding.inflate(inflater, container, false)

        progressBar.visibility = View.VISIBLE
        wordDetailContainer.visibility = View.GONE

        //get data from search fragment
        val args: WordDetailFragmentArgs by navArgs()
        val word = args.myArg

        updateUI(word)
        progressBar.visibility = View.INVISIBLE
        wordDetailContainer.visibility = View.VISIBLE
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}