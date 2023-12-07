package com.example.dictionaryapp.app_features.presentation.search

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.compose.ui.input.key.Key.Companion.W
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dictionaryapp.app_features.domain.model.WordInfo
import com.example.dictionaryapp.databinding.FragmentSearchBinding
import com.example.dictionaryapp.main.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()
    private val searchLayoutContainer get() = binding.searchLayoutContainer
    private val searchLayout get() = binding.layoutSearch
    private val edtInput get() = binding.edtSearchName
    private val progressBar get() = binding.progressBarSearch
    private val rcvContainer get() = binding.rcvContainer
    private val rcvResult get() = binding.rcvSearch
    private var searchResult: List<WordInfo> = emptyList()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        progressBar.visibility = View.INVISIBLE
        rcvContainer.visibility = View.INVISIBLE

        //adapter setting
        val adapter = SearchAdapter(searchResult)
        rcvResult.adapter = adapter
        adapter.setOnClickListener(object : SearchAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                //pass data to detail activity
                val word = searchResult[position]


                //update word is history
                word.isHistory = true
                searchViewModel.updateWords(listOf(word.toWordEntity()!!))
            }
        })

        //search input handle
        edtInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                rcvContainer.visibility = View.VISIBLE
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchViewModel.searchWord(p0.toString())
                rcvContainer.visibility = View.VISIBLE
                Log.e("TAG", "onTextChanged: ")

                // Thực hiện query và trả về kết quả
                requireActivity().collectLatestLifecycleFlow(searchViewModel.result) { result ->
                    if (result.isLoading) {
                        rcvResult.visibility = View.INVISIBLE
                        progressBar.visibility = View.VISIBLE
                        searchResult = result.listWord ?: emptyList()
                    } else {
                        rcvResult.visibility = View.VISIBLE
                        searchResult = result.listWord ?: emptyList()
                    }
                    if (searchResult.isNotEmpty()) {
                        progressBar.visibility = View.GONE
                        adapter.updateData(searchResult)
                    }
                    Log.e("result: ", searchResult.size.toString())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                rcvContainer.visibility = View.VISIBLE
                if(p0.toString().isEmpty()) {
                    rcvContainer.visibility = View.INVISIBLE
                }
            }
        })

        return root
    }


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
    }

}