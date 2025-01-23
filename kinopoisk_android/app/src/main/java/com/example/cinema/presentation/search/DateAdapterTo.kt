package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.DateItemBinding

class DateAdapterTo(private val values: List<Int>, private val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<DateToViewHolder>() {

    private var currentColor: Int = Color.BLACK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateToViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context))
        return DateToViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: DateToViewHolder, position: Int) {
        val dataItem = values.getOrNull(position)

        with(holder.binding){
            textDate.text = dataItem.toString()
        }

        holder.binding.root.setOnClickListener {
            dataItem?.let{
                onClick(dataItem)
            }
            holder.binding.textDate.setBackgroundColor(R.style.searchViewStyle)

            if (currentColor == Color.BLACK) {
                currentColor = Color.BLUE

            } else {
                currentColor = Color.BLACK
            }
            holder.binding.textDate.setTextColor(currentColor)

        }
    }
    override fun getItemCount(): Int = values.size


}
class DateToViewHolder(val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root)