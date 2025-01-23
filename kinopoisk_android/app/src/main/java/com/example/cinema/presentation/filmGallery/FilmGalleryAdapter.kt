package com.example.cinema.presentation.filmGallery

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.databinding.GalleryFilmItemBinding

class FilmGalleryAdapter : RecyclerView.Adapter<MyViewHolder>() {

    private var data: List<FilmGalleryDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilmGalleryDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = GalleryFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            dataItem.let {
                Glide.with(galleryFilm.context)
                    .load(dataItem.imageUrl)
                    .centerCrop()
                    .into(galleryFilm)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}
class MyViewHolder( val binding: GalleryFilmItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}
