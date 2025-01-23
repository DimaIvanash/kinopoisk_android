package com.example.cinema.presentation.HomePages.homePage.popularFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.databinding.PageItemBinding

class PopularFilmsAllAdapter (
    private val onClick: (PopularFilmsDto) -> Unit
) : RecyclerView.Adapter<ViewHolderPopularFilmAll>() {
    private var data: List<PopularFilmsDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PopularFilmsDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPopularFilmAll {
        return ViewHolderPopularFilmAll(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderPopularFilmAll, position: Int) {
        val dataItem = data[position]

        with(holder.binding){
            titleName.text = dataItem.nameRu
            subtitleName.text = dataItem.genres.firstOrNull()?.genre.toString()
            dataItem.let {
                Glide.with(imagePage.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imagePage)
            }
            root.setOnClickListener {
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class ViewHolderPopularFilmAll( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)