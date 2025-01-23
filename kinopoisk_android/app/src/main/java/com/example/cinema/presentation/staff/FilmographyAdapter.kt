package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.InfoActorDto
import com.example.cinema.data.listItems.ListFilmsActor

import com.example.cinema.databinding.FilmographyItemBinding

class FilmographyAdapter (
    private val onClick: (ListFilmsActor) -> Unit
)
    : RecyclerView.Adapter<ViewHolderFilmography>() {

    private var data: List<ListFilmsActor> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListFilmsActor>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFilmography {
        val binding = FilmographyItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderFilmography(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolderFilmography, position: Int) {
        val dataItem = data[position]

        with(holder.binding){
            textName.text = dataItem.nameRu
            textSubtitle.text = dataItem.description
            rating.text = dataItem.rating

            dataItem.let {
                Glide.with(imageView.context)
                    .load(dataItem.filmId)
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
class ViewHolderFilmography(val binding: FilmographyItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}