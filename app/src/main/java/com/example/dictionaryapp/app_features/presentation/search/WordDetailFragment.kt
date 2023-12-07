package com.example.dictionaryapp.app_features.presentation.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.window.OnBackInvokedDispatcher
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.databinding.FragmentWordDetailBinding

class WordDetailFragment : Fragment() {
    private var _binding: FragmentWordDetailBinding? = null
    private val binding get() = _binding!!
    private val progressBar get() = binding.progressBarWordDetail
    private val tvWordDetail get() = binding.tvWordDetail

    private fun updateUI(word: WordInfo?) {
        tvWordDetail.text = word?.word
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWordDetailBinding.inflate(inflater, container, false)
        progressBar.visibility = View.VISIBLE
        tvWordDetail.visibility = View.INVISIBLE

        return binding.root
    }
}