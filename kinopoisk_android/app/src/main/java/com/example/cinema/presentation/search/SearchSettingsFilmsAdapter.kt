package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SearchFilmsSettings
import com.example.cinema.data.SearchKeywordDto
import com.example.cinema.data.listItems.ListItemsSearchSettingsFilms
import com.example.cinema.databinding.SearchKeywordItemBinding
import com.example.cinema.databinding.SearchSettingsFilmsItemBinding

class SearchSettingsFilmsAdapter (
    private val onClick: (SearchFilmsSettings) -> Unit) : RecyclerView.Adapter<SearchFilmsViewHolder>() {

    private var data: List<SearchFilmsSettings> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SearchFilmsSettings>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchFilmsViewHolder {
        val binding = SearchSettingsFilmsItemBinding.inflate(LayoutInflater.from(parent.context))
        return SearchFilmsViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: SearchFilmsViewHolder, position: Int) {
        val dataItem = data[position]

        with(holder.binding){
            textName.text = dataItem.nameRu.toString()
            textSubtitle.text = dataItem.year.toString()
            dataItem.let {
                Glide.with(imageView.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            dataItem.let{
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}
class SearchFilmsViewHolder(val binding: SearchSettingsFilmsItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}