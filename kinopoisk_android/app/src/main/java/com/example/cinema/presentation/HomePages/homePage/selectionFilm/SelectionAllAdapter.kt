package com.example.cinema.presentation.HomePages.homePage.selectionFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.databinding.PageItemBinding

class SelectionAllAdapter (
    private val onClick: (SelectionFilmsDto) -> Unit
) : RecyclerView.Adapter<MyViewHolderAll>() {
    private var data: List<SelectionFilmsDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SelectionFilmsDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderAll {
        return MyViewHolderAll(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolderAll, position: Int) {
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
class MyViewHolderAll( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)