package com.example.dictionaryapp.app_features.presentation.home

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.MainViewModel
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.utils.DismissDuration
import com.example.dictionaryapp.core_utils.wordsconverter.WordsConverterImpl
import com.example.dictionaryapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val activityViewModel: MainViewModel by activityViewModels()
    private val converter = WordsConverterImpl.getInstance()
    private var isHidden = true
    private lateinit var hiddenWord: SpannableString
    private lateinit var showWord: SpannableString
    private val binding get() = _binding!!
    private val word get() = binding.tvWordOriginHidden
    private val meaning get() = binding.tvMeaningQuiz
    private val btnShowKey get() =  binding.btnShowKey
    private val layoutOpts get() = binding.layoutOpts
    private val layoutAnswer get() = binding.layoutAnswer
    private val btnOpts1 get() = binding.btnOpts1
    private val btnOpts2 get() = binding.btnOpts2
    private val btnOpts3 get() = binding.btnOpts3
    private val btnOpts4 get() = binding.btnOpts4
    private val btnOpts5 get() = binding.btnOpts5
    private var list:List<WordInfo> = arrayListOf()
    private var currentIndex = 0
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        homeViewModel.text.observe(viewLifecycleOwner) {
            list = it
            val currentWordIndex = activityViewModel.currentWordIndex
            val res = converter.convertAndColor(it[currentWordIndex].word.toString())
            hiddenWord = res.first
            showWord = converter.revealHiddenChars(res.first, it[currentWordIndex].word.toString(), res.second)
            word.text = hiddenWord
            meaning.text = it[currentWordIndex].meanings[0].definitions[0].definition.toString()
        }

        onClickEvent()

        return root
    }

    private fun onClickEvent() {
        onShowAnswerButton()
        onPreviousButton()
        onNextButton()
        onSoundButton()
        onClickOptsItem()
    }

    private fun onClickOptsItem() {
        btnOpts1.setOnClickListener {
            handleButtonClick(btnOpts1, btnOpts2, btnOpts3, btnOpts4, btnOpts5)
        }

        btnOpts2.setOnClickListener {
            handleButtonClick(btnOpts2, btnOpts1, btnOpts3, btnOpts4, btnOpts5)
        }

        btnOpts3.setOnClickListener {
            handleButtonClick(btnOpts3, btnOpts2, btnOpts1, btnOpts4, btnOpts5)
        }

        btnOpts4.setOnClickListener {
            handleButtonClick(btnOpts4, btnOpts2, btnOpts3, btnOpts1, btnOpts5)
        }

        btnOpts5.setOnClickListener {
            handleButtonClick(btnOpts5, btnOpts2, btnOpts3, btnOpts4, btnOpts1)
        }
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
                activityViewModel.currentWordIndex = currentIndex
                resetState()
            }
        }
    }

    private fun updateUI(index: Int) {
        val res = converter.convertAndColor(list[index].word.toString())
        val wordObj = list[index]
        hiddenWord = res.first
        showWord = converter.revealHiddenChars(res.first, list[index].word.toString(), res.second)
        word.text = hiddenWord
        meaning.text = list[index].meanings[0].definitions[0].definition.toString()

        //Check the options the user has selected (1m, 5m, etc....) and disable button
        val actions: Map<DismissDuration, () -> Unit> = mapOf(
            DismissDuration.ONE_MINUTE to {

            },
            DismissDuration.FIVE_MINUTES to {

            },
            DismissDuration.TEN_MINUTES to {

            },

            DismissDuration.THIRTY_MINUTES to {

            },

            DismissDuration.ONE_DAY to{

            },

            DismissDuration.THREE_DAYS to{

            },

            DismissDuration.FIVE_DAYS to{

            },

            DismissDuration.TEN_DAYS to{

            }
        )
        actions[wordObj.dismissDuration]?.invoke()
    }

    private fun onPreviousButton() {
        binding.btnBack.setOnClickListener {
            if(currentIndex > 0){
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex - 1)
                currentIndex--
                activityViewModel.currentWordIndex = currentIndex
                resetState()
            }
        }
    }

    private fun resetState() {
        isHidden = true
        onShowAnswerButton()
        layoutOpts.visibility = View.GONE
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
                layoutOpts.visibility = View.VISIBLE
            } else{
                word.text = hiddenWord
                btnShowKey.text = "Hiển thị đáp án"
                layoutAnswer.visibility = View.GONE
                isHidden = true
            }
        }
    }

    private fun handleButtonClick(clicked: Button, other1: Button, other2: Button, other3: Button, other4: Button) {
        clicked.isEnabled = false
        other1.isEnabled = true
        other2.isEnabled = true
        other3.isEnabled = true
        other4.isEnabled = true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}