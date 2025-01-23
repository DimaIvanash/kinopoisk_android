package com.example.cinema.presentation.staff

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cinema.data.InfoActorDto
import com.example.cinema.data.listItems.ListFilmsActor
import com.example.cinema.databinding.ActorPageItemBinding

class InfoActorAdapter (
    private val onClick: (ListFilmsActor) -> Unit
) : RecyclerView.Adapter<ViewHolderInfoActor>() {
    private var data: List<ListFilmsActor> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListFilmsActor>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderInfoActor {
        val binding = ActorPageItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderInfoActor(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: ViewHolderInfoActor, position: Int) {
        val dataItem = data[position]
        with(holder.binding){
            titleActorPage.text = dataItem.nameRu?:""
            subtitleNameActorPage.text = dataItem.description?:""
            rating.text = dataItem.rating?:""

            dataItem.let {
                Glide.with(image)
                    .load(dataItem.filmId)
                    .centerCrop()
                    .into(image)
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
class ViewHolderInfoActor(val binding: ActorPageItemBinding) : RecyclerView.ViewHolder(binding.root)  {
}