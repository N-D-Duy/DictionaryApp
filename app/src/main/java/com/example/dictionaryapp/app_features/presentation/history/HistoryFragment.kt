package com.example.dictionaryapp.app_features.presentation.history

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.dictionaryapp.app_features.data.local.entity.HistoryEntity
import com.example.dictionaryapp.app_features.domain.model.Definition
import com.example.dictionaryapp.app_features.domain.model.Meaning
import com.example.dictionaryapp.app_features.domain.use_case.WordUseCases
import com.example.dictionaryapp.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val btnGet get() = binding.btnGet
    private val btnInsert get() = binding.btnInsert
    private val edtWord get() = binding.edtWord
    private val edtPhonetic get() = binding.edtPhonetic
    private val edtMeaning get() = binding.edtMeaning

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel =
            ViewModelProvider(this)[HistoryViewModel::class.java]

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /* val textView: TextView = binding.textNotifications
         historyViewModel.text.observe(viewLifecycleOwner) {
             textView.text = it
         }*/

        //insert data
        btnInsert.setOnClickListener {
            val word = edtWord.text.toString()
            val def = Definition(definition = edtMeaning.text.toString())
            val meaning = Meaning(arrayListOf(def))
            val phonetic = edtPhonetic.text.toString()

            val wordInfo = HistoryEntity(
                id = "001",
                word = word,
                meaning = arrayListOf(meaning),
                phonetic = phonetic
            )
            historyViewModel.insertHistory(wordInfo)
        }

        btnGet.setOnClickListener {

        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}