package com.example.cinema.presentation.HomePages.homePage.popularFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.PageItemBinding

class PopularFilmAdapter (
    private val onClick: (PopularFilmsDto) -> Unit,
    private val onClickAll: (PopularFilmsDto) -> Unit
) : RecyclerView.Adapter<ViewHolderPopularFilm>() {
    private var data: List<PopularFilmsDto> = emptyList()
    private var list: List<FilmEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<PopularFilmsDto>, list: List<FilmEntity>){
        this.data = data
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPopularFilm {
        return ViewHolderPopularFilm(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderPopularFilm, position: Int) {
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
            viewedButton.setOnClickListener {
                onClickAll(dataItem)
            }
            list.forEach{item ->
                if (dataItem.id == item.id){
                    holder.binding.eye.visibility = View.VISIBLE
                }
            }
            if (position == 19) {
                viewedButton.visibility = View.VISIBLE
                textClear.visibility = View.VISIBLE
                imagePage.visibility = View.INVISIBLE
                titleName.visibility = View.INVISIBLE
                ratingContainer.visibility = View.INVISIBLE
                subtitleName.visibility = View.INVISIBLE
            }else{
                viewedButton.visibility= View.INVISIBLE
                textClear.visibility = View.INVISIBLE
                imagePage.visibility = View.VISIBLE
                titleName.visibility= View.VISIBLE
                subtitleName.visibility = View.VISIBLE
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class ViewHolderPopularFilm( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)