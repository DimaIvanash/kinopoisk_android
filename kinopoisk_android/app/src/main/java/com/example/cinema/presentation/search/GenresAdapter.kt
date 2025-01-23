package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.listItems.ListGenres
import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.databinding.GenresItemBinding

class GenresAdapter (
    private val onClick: (ListGenres) -> Unit
)
    : RecyclerView.Adapter<ViewHolderGenres>() {

    private var data: List<ListGenres> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListGenres>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderGenres {
        val binding = GenresItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderGenres(binding)
    }

    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolderGenres, position: Int) {
        val dataItem = data[position]

        holder.binding.nameGenre.text = dataItem.genre
//        holder.binding.container.id = dataItem.id

        holder.binding.root.setOnClickListener {
            dataItem.let{
                onClick(dataItem)
            }
            holder.binding.container.setBackgroundColor(R.color.grey)
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}
class ViewHolderGenres(val binding: GenresItemBinding) : RecyclerView.ViewHolder(binding.root)
