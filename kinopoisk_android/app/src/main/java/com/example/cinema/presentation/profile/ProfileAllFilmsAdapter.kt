package com.example.cinema.presentation.profile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.dataBases.CollectionListFilms
import com.example.cinema.data.dataBases.CollectionWithFilms
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.WatchedMoviesItemBinding

class ProfileAllFilmsAdapter (
    private val onClick: (FilmEntity) -> Unit
) : RecyclerView.Adapter<ProfileAllFilmsViewHolder>() {

    private var data: List<FilmEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilmEntity>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileAllFilmsViewHolder {
        val binding = WatchedMoviesItemBinding.inflate(LayoutInflater.from(parent.context))
        return ProfileAllFilmsViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ProfileAllFilmsViewHolder, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            titleNameWatchedFilm.text = dataItem.nameRu
            dataItem.let {
                Glide.with(imageWatchedFilm.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imageWatchedFilm)
            }
            root.setOnClickListener {
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}
class ProfileAllFilmsViewHolder( val binding: WatchedMoviesItemBinding) : RecyclerView.ViewHolder(binding.root)