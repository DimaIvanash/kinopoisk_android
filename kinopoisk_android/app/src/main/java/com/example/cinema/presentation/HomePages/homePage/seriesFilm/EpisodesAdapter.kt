package com.example.cinema.presentation.HomePages.homePage.seriesFilm
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.data.listItems.ListEpisodes
import com.example.cinema.databinding.EpisodesItemBinding
class EpisodesAdapter: RecyclerView.Adapter<EpisodesViewHolder>() {

    private var data: List<ListEpisodes> = emptyList()
    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ListEpisodes>){
        this.data = data
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodesViewHolder {
        val binding = EpisodesItemBinding.inflate(LayoutInflater.from(parent.context))
        return EpisodesViewHolder(binding)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: EpisodesViewHolder, position: Int) {
        val dataItem = data[position]
        holder.binding.episodes.text = dataItem.episodes.joinToString ("\n") {
            "${it.episodeNumber} Серия. ${it.nameRu} \n ${it.releaseDate.toString()}"
        }
    }
    override fun getItemCount(): Int {
        return data.size
    }
}
class EpisodesViewHolder(val binding: EpisodesItemBinding) : RecyclerView.ViewHolder(binding.root)