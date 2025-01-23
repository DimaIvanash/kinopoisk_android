package com.example.cinema.presentation.HomePages.homePage.premiers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.PremiersFilmsDto
import com.example.cinema.databinding.PageItemBinding

class PremiersFilmsAllAdapter (
    private val onClick: (PremiersFilmsDto) -> Unit
) : RecyclerView.Adapter<ViewHolderPremierFilmAll>() {
    private var data: List<PremiersFilmsDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PremiersFilmsDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPremierFilmAll {
        return ViewHolderPremierFilmAll(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderPremierFilmAll, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            titleName.text = dataItem.nameRu
            subtitleName.text = dataItem.genres.firstOrNull()?.genre.toString()?:""
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
class ViewHolderPremierFilmAll( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)