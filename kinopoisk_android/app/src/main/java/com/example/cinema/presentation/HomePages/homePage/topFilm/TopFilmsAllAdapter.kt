package com.example.cinema.presentation.HomePages.homePage.topFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.TopFilmsDto
import com.example.cinema.databinding.PageItemBinding

class TopFilmsAllAdapter (
    private val onClick: (TopFilmsDto) -> Unit
) : RecyclerView.Adapter<ViewHolderAll>() {

    private var data: List<TopFilmsDto> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TopFilmsDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAll {
        return ViewHolderAll(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderAll, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            titleName.text = dataItem.nameRu?:""
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
class ViewHolderAll( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)