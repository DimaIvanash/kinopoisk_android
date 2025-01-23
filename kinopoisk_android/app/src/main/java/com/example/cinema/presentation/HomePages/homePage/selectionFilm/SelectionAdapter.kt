package com.example.cinema.presentation.HomePages.homePage.selectionFilm

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.PageItemBinding

class SelectionAdapter(
private val onClick: (SelectionFilmsDto) -> Unit,
private val onClickAll: (SelectionFilmsDto) -> Unit
) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<SelectionFilmsDto> = emptyList()
    private var list: List<FilmEntity> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<SelectionFilmsDto>, list: List<FilmEntity>){
        this.data = data
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(PageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    @SuppressLint("SuspiciousIndentation", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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
    fun getGenre(): String{
        return data.firstOrNull()?.genres?.firstOrNull()?.genre.toString()
    }
    fun getCountries(): String{
        return data.firstOrNull()?.countries?.firstOrNull()?.country.toString()

    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class MyViewHolder( val binding: PageItemBinding) : RecyclerView.ViewHolder(binding.root)