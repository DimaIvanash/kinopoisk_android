package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.listItems.ListCountry
import com.example.cinema.data.listItems.ListGenres
import com.example.cinema.databinding.CountryItemBinding
import com.example.cinema.databinding.GenresItemBinding

class CountryAdapter (
    private val onClick: (ListCountry) -> Unit
)
    : RecyclerView.Adapter<ViewHolderCountry>() {

    private var data: List<ListCountry> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListCountry>){
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCountry {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderCountry(binding)
    }

    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolderCountry, position: Int) {
        val dataItem = data[position]

        holder.binding.nameCountry.text = dataItem.country
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
class ViewHolderCountry(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)