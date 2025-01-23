package com.example.cinema.presentation.profile

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.data.dataBases.CollectionEntity
import com.example.cinema.data.dataBases.CollectionWithFilms
import com.example.cinema.databinding.CollectionItemBinding

class ProfileCollectionAdapter(
    private val clickDelete: (CollectionEntity) -> Unit,
    private val clickAllCollection: (CollectionEntity) -> Unit
) : RecyclerView.Adapter<ViewHolderCollection>() {

    private var data: List<CollectionWithFilms> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<CollectionWithFilms>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCollection {
        val binding = CollectionItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderCollection(binding)
    }
    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolderCollection, position: Int) {
        val dataItem = data[position]
        with(holder.binding) {
            textCollection.text = dataItem.collection.nameCollection
            countCollection.text = dataItem.films?.size.toString()
            if (position == 0){
                imageCollection.setImageResource(R.drawable.favorite_black)
                delete.isVisible = false
            }
            if (position == 1){
                imageCollection.setImageResource(R.drawable.bookmark_black)
                delete.isVisible = false
            }
            if (position == 2){
                imageCollection.setImageResource(R.drawable.person_black)
                delete.isVisible = false
            }
            delete.setOnClickListener {
                clickDelete(dataItem.collection)
            }
            containerCollection.setOnClickListener{
                clickAllCollection(dataItem.collection)
            }

        }

    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class ViewHolderCollection(val binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root)