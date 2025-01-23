package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.databinding.SimilarFilmItemBinding

class SimilarFilmAdapter (
    private val onClick: (SimilarFilmDto) -> Unit
) : RecyclerView.Adapter<SimilarViewHolder>() {

    private var data: List<SimilarFilmDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SimilarFilmDto>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarViewHolder {
        val binding = SimilarFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return SimilarViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: SimilarViewHolder, position: Int) {
        val dataItem = data.getOrNull(position)

        with(holder.binding){
            titleNameSimilarFilm.text = dataItem?.nameRu
            dataItem.let {
                Glide.with(imageSimilarFilm.context)
                    .load(dataItem?.posterUrl)
                    .centerCrop()
                    .into(imageSimilarFilm)
            }
            root.setOnClickListener {
                if (dataItem != null) {
                    onClick(dataItem)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}
class SimilarViewHolder(val binding: SimilarFilmItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}