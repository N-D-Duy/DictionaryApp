package com.example.dictionaryapp.app_features.utils.optionsupport

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictionaryapp.R
import com.example.dictionaryapp.app_features.utils.DismissDuration

class OptionsAdapter(
    private val items: List<DismissDuration>
): RecyclerView.Adapter<OptionsAdapter.ViewHolder>() {
//    private val colors = intArrayOf(R.color.orange, R.color.green, R.color.purple, R.color.yellow, R.color.violent)
    private val colors = intArrayOf(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA)
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnClickListener(clickListener: OnItemClickListener) {
        mListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_options, parent,  false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val colorIndex = position % 5
        val color = colors[colorIndex]
        holder.bind(items[position], mListener, color)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val btnTime: TextView = itemView.findViewById(R.id.btn_time_options)

        fun bind(time: DismissDuration, listener: OnItemClickListener, color: Int) {
            //gán giá trị các fields của bạn cho recyclerview
            when(time){
                DismissDuration.ONE_MINUTE ->{
                    btnTime.text = "1m"
                }
                DismissDuration.FIVE_MINUTES ->{
                    btnTime.text = "5m"
                }
                DismissDuration.TEN_MINUTES ->{
                    btnTime.text = "10m"
                }
                DismissDuration.THIRTY_MINUTES ->{
                    btnTime.text = "30m"
                }
                DismissDuration.ONE_DAY ->{
                    btnTime.text = "1d"
                }
                DismissDuration.THREE_DAYS ->{
                    btnTime.text = "3d"
                }
                DismissDuration.FIVE_DAYS ->{
                    btnTime.text = "5d"
                }
                DismissDuration.TEN_DAYS ->{
                    btnTime.text = "10d"
                }
                DismissDuration.ONE_MONTH ->{
                    btnTime.text = "1M"
                }
                DismissDuration.THREE_MONTHS ->{
                    btnTime.text = "3M"
                }
                DismissDuration.FIVE_MONTHS ->{
                    btnTime.text = "5M"
                }
                DismissDuration.ONE_YEAR ->{
                    btnTime.text = "1y"
                }
            }
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
            btnTime.setBackgroundColor(color)
        }
    }
}