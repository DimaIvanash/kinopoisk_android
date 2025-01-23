package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SeriesFilmsDto
import com.example.cinema.data.dataBases.CollectionWithFilms
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.WatchedMoviesItemBinding

class WatchedMoviesAdapter(
    private val clickDeleteAllFilms: (FilmEntity) -> Unit,
    private val onClick: (FilmEntity) -> Unit
) : RecyclerView.Adapter<WatchedViewHolder>() {

    private var data: List<FilmEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FilmEntity>){
        this.data = data
        notifyDataSetChanged()

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchedViewHolder {
        val binding = WatchedMoviesItemBinding.inflate(LayoutInflater.from(parent.context))
        return WatchedViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: WatchedViewHolder, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            titleNameWatchedFilm.text = dataItem.nameRu
            dataItem.let {
                Glide.with(imageWatchedFilm.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imageWatchedFilm)
            }
            if (position == data.size -1){
                clearButton.visibility = View.VISIBLE
                textClear.visibility = View.VISIBLE
                imageWatchedFilm.visibility = View.INVISIBLE
                titleNameWatchedFilm.visibility = View.INVISIBLE
            }else{
                clearButton.visibility = View.INVISIBLE
                textClear.visibility = View.INVISIBLE
                imageWatchedFilm.visibility = View.VISIBLE
                titleNameWatchedFilm.visibility = View.VISIBLE
            }
            clearButton.setOnClickListener {
                clickDeleteAllFilms(dataItem)
            }
            root.setOnClickListener {
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int = data.size
}
class WatchedViewHolder( val binding: WatchedMoviesItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}

