package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.StaffJobFilmDto
import com.example.cinema.databinding.StaffJobFilmItemBinding

class StaffJobFilmAdapter( private val onClick: (StaffJobFilmDto) -> Unit ) : RecyclerView.Adapter<ViewHolder>() {
    private var data: List<StaffJobFilmDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<StaffJobFilmDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = StaffJobFilmItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataItem = data.getOrNull(position)

        with(holder.binding){
            textName.text = dataItem?.nameRu?: ""
            textDescription.text = dataItem?.professionText?: ""

            dataItem.let {
                Glide.with(imageStaff.context)
                    .load(dataItem?.posterUrl)
                    .centerCrop()
                    .into(imageStaff)
            }
            root.setOnClickListener {
                dataItem?.let{
                    onClick(dataItem)
                }
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }

}
class ViewHolder(val binding: StaffJobFilmItemBinding) : RecyclerView.ViewHolder(binding.root)
