package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.SearchKeywordDto
import com.example.cinema.data.listItems.ListItemsFilmsKeyword
import com.example.cinema.databinding.SearchKeywordItemBinding

class SearchKeywordAdapter(
    private val onClick: (ListItemsFilmsKeyword) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<ListItemsFilmsKeyword> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListItemsFilmsKeyword>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = SearchKeywordItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            textName.text = dataItem.nameRu?:""
            textSubtitle.text = dataItem.year.toString()


            dataItem.let {
                Glide.with(imageView.context)
                    .load(dataItem.posterUrl)
                    .centerCrop()
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            onClick(dataItem)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class MyViewHolder(val binding: SearchKeywordItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}