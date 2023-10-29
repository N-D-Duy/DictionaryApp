package com.example.dictionaryapp.app_features.presentation.home

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.core_utils.wordsconverter.WordsConverterImpl
import com.example.dictionaryapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val converter = WordsConverterImpl.getInstance()
    private var isHidden = true
    private lateinit var hiddenWord: SpannableString
    private lateinit var showWord: SpannableString
    private val binding get() = _binding!!
    private val word get() = binding.tvWordOriginHidden
    private val meaning get() = binding.tvMeaningQuiz
    private val btnShowKey get() =  binding.btnShowKey
    private val layoutAnswer get() = binding.layoutAnswer
    private var list:List<WordInfo> = arrayListOf()
    private var currentIndex = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        homeViewModel.text.observe(viewLifecycleOwner) {
            list = it
            val res = converter.convertAndColor(it[0].word.toString())
            hiddenWord = res.first
            showWord = converter.revealHiddenChars(res.first, it[0].word.toString(), res.second)
            word.text = hiddenWord
            meaning.text = it[0].meanings[0].definitions[0].definition.toString()
        }
        onClickEvent()
        return root
    }

    private fun onClickEvent() {
        onShowAnswerButton()
        onPreviousButton()
        onNextButton()
        onSoundButton()
    }

    private fun onSoundButton() {
        binding.btnVoice.setOnClickListener {

        }
    }

    private fun onNextButton() {
        binding.btnForward.setOnClickListener {
            if(currentIndex < list.size-1){
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex + 1)
                currentIndex++
                resetState()
            }
        }
    }

    private fun updateUI(index: Int) {
        val res = converter.convertAndColor(list[index].word.toString())
        hiddenWord = res.first
        showWord = converter.revealHiddenChars(res.first, list[index].word.toString(), res.second)
        word.text = hiddenWord
        meaning.text = list[index].meanings[0].definitions[0].definition.toString()
    }

    private fun onPreviousButton() {
        binding.btnBack.setOnClickListener {
            if(currentIndex > 0){
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex - 1)
                currentIndex--
                resetState()
            }
        }
    }

    private fun resetState() {
        isHidden = true
        onShowAnswerButton()
    }

    private fun onShowAnswerButton() {
        if(isHidden){
            btnShowKey.text = "Hiển thị đáp án"
        } else{
            btnShowKey.text = "Ẩn đáp án"
        }
        btnShowKey.setOnClickListener {
            if(isHidden){
                word.text = showWord
                btnShowKey.text = "Ẩn đáp án"
                layoutAnswer.visibility = View.VISIBLE
                isHidden = false
            } else{
                word.text = hiddenWord
                btnShowKey.text = "Hiển thị đáp án"
                layoutAnswer.visibility = View.GONE
                isHidden = true
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}