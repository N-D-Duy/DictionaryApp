package com.example.dictionaryapp.app_features.presentation.home

import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val activityViewModel: MainViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val converter = WordsConverterImpl.getInstance()
    private var isHiddenAnswer = true
    private var isHiddenOptions = false
    private var isPlaying = false
    private lateinit var hiddenWord: SpannableString
    private lateinit var showWord: SpannableString
    private val binding get() = _binding!!
    private val word get() = binding.tvWordOriginHidden
    private val meaning get() = binding.tvMeaningQuiz
    private val btnShowKey get() = binding.btnShowKey
    private val btnSound get() = binding.btnVoice
    private val layoutOpts get() = binding.layoutOpts
    private val layoutAnswer get() = binding.layoutAnswer
    private val tvPhonetic get() = binding.tvWordHiddenPhonetic
    private val progressBar get() = binding.homeProgressBar
    private val layoutMain get() = binding.layoutHomeMain
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
    private var listWord: List<WordInfo> = arrayListOf()
    private var currentIndex = 0
    private lateinit var ttsListener: TTSListener


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel.fetchRandomUnusedWords()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //init
        ttsListener = TTSListener(requireContext().applicationContext) {
            resetPlayingButton()
        }
        lifecycle.addObserver(ttsListener)


        //set adapter
        rcvOptions.layoutManager = LinearLayoutManager(
            requireContext().applicationContext,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rcvOptions.hasFixedSize()

        val mAdapter = OptionsAdapter(items)
        rcvOptions.adapter = mAdapter

        mAdapter.setOnClickListener(object : OptionsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                isHiddenOptions = true
                listWord[currentIndex].dismissDuration = items[position]
                layoutOpts.visibility = View.GONE
                val time = ConvertTime(items[position].durationInMinutes).convertMinutesToTime()
                Toast.makeText(context, "This word will not appear after $time", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        requireActivity().collectLatestLifecycleFlow(homeViewModel.multipleWordsState) {
            if (it.isLoading) {
                UIEvent.ShowSnackBar("Loading...")
                progressBar.visibility = View.VISIBLE
                layoutMain.visibility = View.GONE
                listWord = it.wordList ?: emptyList()
            } else {
                progressBar.visibility = View.GONE
                layoutMain.visibility = View.VISIBLE
                listWord = it.wordList ?: emptyList()
            }
            if (listWord.isNotEmpty()) {
                progressBar.visibility = View.GONE
                layoutMain.visibility = View.VISIBLE
                initialSetting()
            }else{
                UIEvent.ShowSnackBar("Loading...")
                progressBar.visibility = View.VISIBLE
                layoutMain.visibility = View.GONE
            }
        }


        //set event
        onClickEvent()

        return root
    }

    private fun initialSetting() {
        val currentWordIndex = activityViewModel.currentWordIndex
        val res = converter.convertAndColor(listWord[currentWordIndex].word.toString())
        hiddenWord = res.first
        showWord = converter.revealHiddenChars(
            res.first,
            listWord[currentWordIndex].word.toString(),
            res.second
        )
        tvPhonetic.text = listWord[currentWordIndex].phonetic.toString()
        word.text = hiddenWord
        meaning.text = listWord[currentWordIndex].meanings[0].definitions[0].definition.toString()
    }


    private fun onClickEvent() {
        onShowAnswerButton()
        onPreviousButton()
        onNextButton()
        onSoundButton()
    }


    //generic collection support method
    private fun <T> ComponentActivity.collectLatestLifecycleFlow(
        flow: Flow<T>,
        collect: suspend (T) -> Unit
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }

    //phonetic button
    private fun onSoundButton() {

        btnSound.setOnClickListener {
            val currentWord = listWord[currentIndex]
            isPlaying = if (!isPlaying) {
                ttsListener.speak(currentWord.word.toString())
                changeIconButtonPlay()
                true
            } else {
                ttsListener.stop()
                false
            }
        }
    }

    private fun changeIconButtonPlay() {
        btnSound.setImageResource(R.drawable.icon_playing)
    }

    //reset icon button
    private fun resetPlayingButton() {
        btnSound.setImageResource(R.drawable.icon_stop)
    }

    private fun onNextButton() {
        binding.btnForward.setOnClickListener {
            if (currentIndex < listWord.size - 1) {
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex + 1)
                currentIndex++
                activityViewModel.currentWordIndex = currentIndex
                resetState()
            }
        }
    }

    //update UI
    private fun updateUI(index: Int) {
        val res = converter.convertAndColor(listWord[index].word.toString())
        hiddenWord = res.first
        showWord =
            converter.revealHiddenChars(res.first, listWord[index].word.toString(), res.second)
        word.text = hiddenWord
        tvPhonetic.text = listWord[index].phonetic.toString()
        meaning.text = listWord[index].meanings[0].definitions[0].definition.toString()
    }

    //previous button
    private fun onPreviousButton() {
        binding.btnBack.setOnClickListener {
            if (currentIndex > 0) {
                layoutAnswer.visibility = View.GONE
                updateUI(currentIndex - 1)
                currentIndex--
                activityViewModel.currentWordIndex = currentIndex
                resetState()
            }
        }
    }

    //reset state after click next or previous button
    private fun resetState() {
        isHiddenAnswer = true
        onShowAnswerButton()
        layoutOpts.visibility = View.GONE
        isHiddenOptions = false
    }

    //show or hide answer
    private fun onShowAnswerButton() {
        if (isHiddenAnswer) {
            btnShowKey.text = "Hiển thị đáp án"
        } else {
            btnShowKey.text = "Ẩn đáp án"
        }
        btnShowKey.setOnClickListener {
            if (isHiddenAnswer) {
                word.text = showWord
                btnShowKey.text = "Ẩn đáp án"
                if (!isHiddenOptions) layoutOpts.visibility = View.VISIBLE
                layoutAnswer.visibility = View.VISIBLE
                isHiddenAnswer = false

            } else {
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