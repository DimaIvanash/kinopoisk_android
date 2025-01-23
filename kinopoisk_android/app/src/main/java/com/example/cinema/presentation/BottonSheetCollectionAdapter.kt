package com.example.cinema.presentation

import android.annotation.SuppressLint
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.data.dataBases.CollectionWithFilms
import com.example.cinema.databinding.AddCollectionItemBinding
class BottonSheetCollectionAdapter(
    private var onClick: (state: String, CollectionWithFilms) -> Unit
) : RecyclerView.Adapter<ViewHolderCollection>() {

    private var data: List<CollectionWithFilms> = emptyList()
    private val checkedState = SparseBooleanArray()
    private var state = ""
    @SuppressLint("NotifyDataSetChanged")
    fun setData(element: List<CollectionWithFilms>){
        data = element
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCollection {
        val binding = AddCollectionItemBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolderCollection(binding)
    }
    @SuppressLint("SuspiciousIndentation", "ResourceAsColor")
    override fun onBindViewHolder(holder: ViewHolderCollection, position: Int) {
        val dataItem = data.getOrNull(position)

        with(holder.binding){
            addNewCollection.text = dataItem?.collection?.nameCollection ?:""
            count.text = dataItem?.films?.size.toString()
            checkboxSheet.isChecked = checkedState.get(position, false)

            checkboxSheet.setOnClickListener{
                if (!checkedState.get(position,false)){
                    checkboxSheet.isChecked = true
                    checkedState.put(position, true)
                    state = LIST_STATE_ADD
                }else{
                    state = LIST_STATE_REMOVE
                    checkboxSheet.isChecked = false
                    checkedState.put(position, false)
                }
                dataItem?.let { onClick(state, dataItem) }
            }
        }
    }
    companion object{
        private const val LIST_STATE_ADD = "LIST_STATE_ADD"
        private const val LIST_STATE_REMOVE = "LIST_STATE_REMOVE"
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class ViewHolderCollection(val binding: AddCollectionItemBinding) : RecyclerView.ViewHolder(binding.root)