package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.databinding.StaffItemBinding

class StaffAdapter(private val onClick: (InfoStaffDto) -> Unit) : RecyclerView.Adapter<MyViewHolder>() {
    private var data: List<InfoStaffDto> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<InfoStaffDto>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = StaffItemBinding.inflate(LayoutInflater.from(parent.context))
        return MyViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = data.getOrNull(position)

        with(holder.binding){
            textName.text = dataItem?.nameRu?: ""
            textDescription.text = dataItem?.description?: ""

            dataItem.let {
                Glide.with(imageStaff.context)
                    .load(dataItem?.posterUrl)
                    .centerCrop()
                    .into(imageStaff)
            }
        }
        holder.binding.root.setOnClickListener {
            dataItem?.let{
                onClick(dataItem)
            }
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class MyViewHolder(val binding: StaffItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}