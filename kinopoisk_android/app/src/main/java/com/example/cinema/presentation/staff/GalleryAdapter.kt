package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.FilmGalleryDto

import com.example.cinema.databinding.GalleryItemBinding


class GalleryAdapter ( private val onClick: (FilmGalleryDto) -> Unit) : RecyclerView.Adapter<GalleryViewHolder>() {
    private var data: List<FilmGalleryDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilmGalleryDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val binding = GalleryItemBinding.inflate(LayoutInflater.from(parent.context))
        return GalleryViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val dataItem = data.getOrNull(position)

        with(holder.binding){

            dataItem.let {
                Glide.with(imageView2.context)
                    .load(dataItem?.imageUrl)
                    .centerCrop()
                    .into(imageView2)
            }
        }
        holder.binding.root.setOnClickListener {
            dataItem?.let{
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class GalleryViewHolder(val binding: GalleryItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}