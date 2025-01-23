package com.example.cinema.presentation.HomePages.homePage

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.CollectionSearchItemBinding
class CollectionAdapter(
    private val onClick: (FilmEntity) -> Unit
) : RecyclerView.Adapter<WatchedViewHolder>() {

    private var data: List<FilmEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilmEntity>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedViewHolder {
        val binding = CollectionSearchItemBinding.inflate(LayoutInflater.from(parent.context))
        return WatchedViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: WatchedViewHolder, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            textName.text = dataItem.nameRu
            dataItem.let {
                Glide.with(imageView.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imageView)
            }
            root.setOnClickListener {
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}
class WatchedViewHolder( val binding: CollectionSearchItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}