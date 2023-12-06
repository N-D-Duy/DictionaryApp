package com.example.dictionaryapp.app_features.presentation.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.domain.model.WordInfo


class SearchAdapter(private var list: List<WordInfo>): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val tvWord = itemView.findViewById(R.id.tv_word_result_search) as TextView
        private val imageHistory = itemView.findViewById(R.id.img_history_search) as ImageView
        fun bind(wordInfo: WordInfo, listener: OnItemClickListener) {
            tvWord.text = wordInfo.word.trim()
            if(wordInfo.isHistory) {
                imageHistory.visibility = View.VISIBLE
            } else {
                imageHistory.visibility = View.GONE
            }
            //gán giá trị các fields của bạn cho recyclerview
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_search, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], mListener)
    }
    fun updateData(list: List<WordInfo>) {
        this.list = list
        notifyDataSetChanged()
    }
}
