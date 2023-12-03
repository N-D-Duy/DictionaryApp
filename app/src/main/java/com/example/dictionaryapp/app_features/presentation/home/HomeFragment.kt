package com.example.dictionaryapp.app_features.presentation.home

import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionaryapp.MainViewModel
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.app_features.utils.DismissDuration
import com.example.dictionaryapp.app_features.utils.optionsupport.ConvertTime
import com.example.dictionaryapp.app_features.utils.optionsupport.OptionsAdapter
import com.example.dictionaryapp.core_utils.text_to_speech.TTSListener
import com.example.dictionaryapp.core_utils.wordsconverter.WordsConverterImpl
import com.example.dictionaryapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.reflect.typeOf


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val activityViewModel: MainViewModel by activityViewModels()
    private val converter = WordsConverterImpl.getInstance()
    private var isHiddenAnswer = true
    private var isHiddenOptions = false
    private var isPlaying = false
    private lateinit var hiddenWord: SpannableString
    private lateinit var showWord: SpannableString
    private val binding get() = _binding!!
    private val word get() = binding.tvWordOriginHidden
    private val meaning get() = binding.tvMeaningQuiz
    private val btnShowKey get() =  binding.btnShowKey
    private val btnSound get() = binding.btnVoice
    private val layoutOpts get() = binding.layoutOpts
    private val layoutAnswer get() = binding.layoutAnswer
    private val rcvOptions get() = binding.rcvOptions
    private val items: List<DismissDuration> = arrayListOf(
        DismissDuration.ONE_MINUTE,
        DismissDuration.FIVE_MINUTES,
        DismissDuration.TEN_MINUTES,
        DismissDuration.THIRTY_MINUTES,
        DismissDuration.ONE_DAY,
        DismissDuration.THREE_DAYS,
        DismissDuration.FIVE_DAYS,
        DismissDuration.TEN_DAYS,
        DismissDuration.ONE_MONTH,
        DismissDuration.THREE_MONTHS,
        DismissDuration.FIVE_MONTHS,
        DismissDuration.ONE_YEAR
    )
    private var listWord:List<WordInfo> = arrayListOf()
    private var currentIndex = 0
    private lateinit var ttsListener: TTSListener
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //init
        ttsListener = TTSListener(requireContext().applicationContext){
            resetPlayingButton()
        }
        lifecycle.addObserver(ttsListener)


        //set adapter
        rcvOptions.layoutManager = LinearLayoutManager(requireContext().applicationContext, LinearLayoutManager.HORIZONTAL, false)
        rcvOptions.hasFixedSize()

        val mAdapter = OptionsAdapter(items)
        rcvOptions.adapter = mAdapter

        //create initial view
        homeViewModel.text.observe(viewLifecycleOwner) {
            listWord = it
            val currentWordIndex = activityViewModel.currentWordIndex
            val res = converter.convertAndColor(it[currentWordIndex].word.toString())
            hiddenWord = res.first
            showWord = converter.revealHiddenChars(res.first, it[currentWordIndex].word.toString(), res.second)
            word.text = hiddenWord
            meaning.text = it[currentWordIndex].meanings[0].definitions[0].definition.toString()
        }

        mAdapter.setOnClickListener(object: OptionsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                isHiddenOptions = true
                listWord[currentIndex].dismissDuration = items[position]
                layoutOpts.visibility = View.GONE
                val time = ConvertTime(items[position].durationInMinutes).convertMinutesToTime()
                Toast.makeText(context, "This word will not appear after $time", Toast.LENGTH_SHORT).show()
            }
        })


        //set event
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

        btnSound.setOnClickListener {
            val currentWord = listWord[currentIndex]
            isPlaying = if(!isPlaying){
                ttsListener.speak(currentWord.word.toString())
                changeIconButtonPlay()
                true
            } else{
                ttsListener.stop()
                false
            }
        }
    }

    private fun changeIconButtonPlay() {
        btnSound.setImageResource(R.drawable.icon_playing)
    }

    private fun resetPlayingButton(){
        btnSound.setImageResource(R.drawable.icon_stop)
    }

    private fun onNextButton() {
        binding.btnForward.setOnClickListener {
            if(currentIndex < listWord.size-1){
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex + 1)
                currentIndex++
                activityViewModel.currentWordIndex = currentIndex
                resetState()
            }
        }
    }

    private fun updateUI(index: Int) {
        val res = converter.convertAndColor(listWord[index].word.toString())
        hiddenWord = res.first
        showWord = converter.revealHiddenChars(res.first, listWord[index].word.toString(), res.second)
        word.text = hiddenWord
        meaning.text = listWord[index].meanings[0].definitions[0].definition.toString()
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
        isHiddenAnswer = true
        onShowAnswerButton()
        layoutOpts.visibility = View.GONE
        isHiddenOptions = false
    }

    private fun onShowAnswerButton() {
        if(isHiddenAnswer){
            btnShowKey.text = "Hiển thị đáp án"
        } else{
            btnShowKey.text = "Ẩn đáp án"
        }
        btnShowKey.setOnClickListener {
            if(isHiddenAnswer){
                word.text = showWord
                btnShowKey.text = "Ẩn đáp án"
                if (!isHiddenOptions) layoutOpts.visibility = View.VISIBLE
                layoutAnswer.visibility = View.VISIBLE
                isHiddenAnswer = false

            } else{
                word.text = hiddenWord
                btnShowKey.text = "Hiển thị đáp án"
                layoutAnswer.visibility = View.GONE
                layoutOpts.visibility = View.GONE
                isHiddenAnswer = true
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        lifecycle.removeObserver(ttsListener)
    }
}