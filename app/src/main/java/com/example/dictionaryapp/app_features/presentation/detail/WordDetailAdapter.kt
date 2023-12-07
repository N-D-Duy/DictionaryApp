package com.example.dictionaryapp.app_features.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.model.Definition
import com.example.dictionaryapp.app_features.domain.model.WordInfo

class MeaningViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val partOfSpeech: TextView = itemView.findViewById(R.id.tv_part_of_speech)
    val definitionsRecyclerView: RecyclerView = itemView.findViewById(R.id.rcv_definitions)
}

class DefinitionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val definition: TextView = itemView.findViewById(R.id.tv_definition_item)
    val example: TextView = itemView.findViewById(R.id.tv_example_item)
}

class WordDetailAdapter(private val wordInfo: WordInfo) :
    RecyclerView.Adapter<MeaningViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_meaning, parent, false)
        return MeaningViewHolder(view)
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val meaning = wordInfo.meanings[position]
        holder.partOfSpeech.text = meaning.partOfSpeech

        val definitionsAdapter = DefinitionsAdapter(meaning.definitions)
        holder.definitionsRecyclerView.adapter = definitionsAdapter
        holder.definitionsRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context)
    }

    override fun getItemCount(): Int {
        return wordInfo.meanings.size
    }
}

class DefinitionsAdapter(private val definitions: List<Definition>) :
    RecyclerView.Adapter<DefinitionViewHolder>() {
    private var currentIndex = 0
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_definition, parent, false)
        return DefinitionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DefinitionViewHolder, position: Int) {
        val definition = definitions[position]
        if (definition.definition.isNullOrEmpty()) {
            holder.definition.visibility = View.GONE
            currentIndex++
        } else {
            val definitionText = "${position-currentIndex+1}. " + definition.definition
            holder.definition.text = definitionText
        }

        if(definition.example.isNullOrEmpty()){
            holder.example.visibility = View.GONE
        } else{
            val exampleText = "Example: " + definition.example
            holder.example.visibility = View.VISIBLE
            holder.example.text = exampleText
        }


    }

    override fun getItemCount(): Int {
        return definitions.size
    }
}