package com.example.cinema.presentation.HomePages.homePage.seriesFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.data.listItems.ListEpisodes
import com.example.cinema.databinding.SeriesNumberItemBinding
class SeriesNumberAdapter(private val onClick: (Int) -> Unit) : RecyclerView.Adapter<SeriesViewHolder>() {
    private var data: List<ListEpisodes> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListEpisodes>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeriesViewHolder {
        val binding = SeriesNumberItemBinding.inflate(LayoutInflater.from(parent.context))
        return SeriesViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SeriesViewHolder, position: Int) {
        val dataItem = data[position]
        holder.binding.textNumber.text = dataItem.number.toString()
        holder.binding.root.setOnClickListener {
            onClick(dataItem.number)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class SeriesViewHolder(val binding: SeriesNumberItemBinding) : RecyclerView.ViewHolder(binding.root)