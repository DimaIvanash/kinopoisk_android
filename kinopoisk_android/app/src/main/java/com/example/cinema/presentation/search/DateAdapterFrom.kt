package com.example.cinema.presentation.search

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.databinding.DateItemBinding

class DateAdapterFrom(private val values: List<Int>, private val onClick: (Int) -> Unit)
    : RecyclerView.Adapter<DateViewHolder>() {

    private var currentColor: Int = Color.BLACK

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val binding = DateItemBinding.inflate(LayoutInflater.from(parent.context))
        return DateViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: DateViewHolder, position: Int) {
        val dataItem = values.getOrNull(position)

        with(holder.binding){
            textDate.text = dataItem.toString()
        }

        holder.binding.root.setOnClickListener {
            dataItem?.let{
                onClick(dataItem)
            }
            holder.binding.textDate.apply {
                setBackgroundColor(R.style.searchViewStyle)
            }
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
class DateViewHolder(val binding: DateItemBinding) : RecyclerView.ViewHolder(binding.root)
