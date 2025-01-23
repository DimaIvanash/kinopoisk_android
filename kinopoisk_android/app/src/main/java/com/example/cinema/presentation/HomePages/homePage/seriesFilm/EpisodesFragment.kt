package com.example.cinema.presentation.HomePages.homePage.seriesFilm


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentEpisodesBinding
import com.example.cinema.presentation.HomePages.homePage.HomePageViewModel
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.CLICK_SERIES_FILMS

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EpisodesFragment : Fragment() {
    companion object{
       const val ID = "id"
    }
    private  val viewModel: HomePageViewModel by viewModels()
    private var _binding : FragmentEpisodesBinding? = null
    private val binding get() = _binding!!

    private val episodesAdapter = EpisodesAdapter()
    private var seriesNumber = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(layoutInflater)
        return binding.root
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idEpisode = arguments?.getInt(ID)!!
        val bundle = Bundle()
        val visibleSeries = true
        bundle.putBoolean(CLICK_SERIES_FILMS, visibleSeries )
        bundle.putInt(ID, idEpisode)

        val seriesNumberAdapter = SeriesNumberAdapter{ season -> onClickSeries(season, idEpisode)}

        binding.recyclerSeasons.adapter = seriesNumberAdapter
        binding.recyclerEpisodes.adapter = episodesAdapter
        binding.recyclerSeasons.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerEpisodes.layoutManager = LinearLayoutManager(
            requireContext(), LinearLayoutManager.VERTICAL, false)
        viewLifecycleOwner.lifecycleScope.launch {
            seriesNumberAdapter.setData(viewModel.getEpisodes(idEpisode))
            onClickSeries(seriesNumber, idEpisode)
        }
        binding.outlineInt.setOnClickListener {
            findNavController().navigate(R.id.action_episodesFragment_to_infoFilmFragment, bundle)
        }
    }
    private fun onClickSeries(season: Int, idEpisode: Int) {
        this.seriesNumber = season
        viewLifecycleOwner.lifecycleScope.launch {
            episodesAdapter.setData(viewModel.getEpisodes(idEpisode).filter { season -> season.number == seriesNumber })
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}




