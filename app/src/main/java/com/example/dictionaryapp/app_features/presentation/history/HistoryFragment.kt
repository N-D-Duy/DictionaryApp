package com.example.dictionaryapp.app_features.presentation.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.domain.model.Definition
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val historyViewModel: HistoryViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val btnGet get() = binding.btnGet
    private val btnInsert get() = binding.btnInsert
    private val edtWord get() = binding.edtWord
    private val edtPhonetic get() = binding.edtPhonetic
    private val edtMeaning get() = binding.edtMeaning
    private var isClickBtnGet = false
    private val btnDownload get() = binding.btnDownload

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //insert data
        btnInsert.setOnClickListener {
            val word = edtWord.text.toString()
            val def = Definition(definition = edtMeaning.text.toString())
            val meaning = Meaning(arrayListOf(def))
            val phonetic = edtPhonetic.text.toString()

            val wordInfo = HistoryEntity(
                id = 0,
                word = word,
                meaning = arrayListOf(meaning),
                phonetic = phonetic
            )
            historyViewModel.insertHistory(wordInfo)
        }

        //collect data
        requireActivity().collectLatestLifecycleFlow(historyViewModel.singleWordState) {
            if (isClickBtnGet) {
                val wordInfo = it.wordInfo
                if (it.isLoading) {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "loading....",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    Toast.makeText(
                        requireContext().applicationContext,
                        "get word successful: ${wordInfo?.word.toString()}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        btnGet.setOnClickListener {
            val word = edtWord.text.toString().trim()
            historyViewModel.getHistoryWord(word)
            isClickBtnGet = true
        }

        btnDownload.setOnClickListener {
            historyViewModel.downLoadMoreWord()
        }
        return root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        isClickBtnGet = false
    }


}