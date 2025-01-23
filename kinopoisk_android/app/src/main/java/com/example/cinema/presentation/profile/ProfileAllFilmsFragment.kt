package com.example.cinema.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.data.PremiersFilmsDto
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.data.SelectionFilmsTwoDto
import com.example.cinema.data.SeriesFilmsDto
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.data.TopFilmsDto
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.data.listItems.ListFilmsActor
import com.example.cinema.databinding.FragmentProfileAllFilmsBinding
import com.example.cinema.presentation.HomePages.homePage.ActorPageFragment
import com.example.cinema.presentation.HomePages.homePage.HomePageViewModel
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment
import com.example.cinema.presentation.HomePages.homePage.popularFilm.PopularFilmsAllAdapter
import com.example.cinema.presentation.HomePages.homePage.premiers.PremiersFilmsAllAdapter
import com.example.cinema.presentation.HomePages.homePage.selectionFilm.SelectionAllAdapter
import com.example.cinema.presentation.HomePages.homePage.selectionFilmTwo.SelectionTwoAllAdapter
import com.example.cinema.presentation.HomePages.homePage.seriesFilm.SeriesAllAdapter
import com.example.cinema.presentation.staff.InfoActorAdapter
import com.example.cinema.presentation.staff.SimilarFilmAdapter
import com.example.cinema.presentation.staff.StaffAdapter
import com.example.cinema.presentation.HomePages.homePage.topFilm.TopFilmsAllAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProfileAllFilmsFragment : Fragment() {
    companion object{
        const val COLLECTION_ID = "collection_id"
        const val CLICK_VIEWED_COLLECTION = "click_viewed_collection"
        const val CLICK_LOOK_COLLECTION = "click_look_collection"
        const val CLICK_TOP_FILMS_ALL = "click_top_films_all"
        const val CLICK_PREMIERS_FILMS_ALL = "click_premiers_films_all"
        const val CLICK_POPULAR_FILMS_ALL = "click_popular_films_all"
        const val CLICK_SELECTION_ONE_ALL = "click_selection_one_all"
        const val CLICK_SELECTION_TWO_ALL = "click_selection_two_all"
        const val CLICK_SERIES_FILMS_ALL = "click_series_films_all"
        const val CLICK_ALL_ACTOR_ID = "click_all_actor_id"
        const val CLICK_ALL_RELATED_FILM = "click_all_related_film"
        const val FILM_ID_ACTOR = "film_id_actor"
        const val FILM_ACTOR_TRUE = "film_actor_true"
    }
    private val viewModel: HomePageViewModel by viewModels()
    private var _binding : FragmentProfileAllFilmsBinding? = null
    private val binding get() = _binding!!
    private val onClickFilm: (clickFilm : FilmEntity) -> Unit = { filmWidthCollection ->
        onClickFilm(filmWidthCollection)
    }
    private val onItemClickTopFilm: (topFilm: TopFilmsDto) -> Unit = {
        topFilm -> onItemClickTopFilm(topFilm)
    }
    private val onItemClick: (premiersFilm: PremiersFilmsDto) -> Unit = {
        premiersFilm -> onItemClick(premiersFilm)
    }
    private val onItemClickPopularFilm: (popularFilm: PopularFilmsDto) -> Unit = {
        popularFilm -> onItemClickPopularFilm(popularFilm)
    }
    private val onItemClickSelection: (selectionOne: SelectionFilmsDto) -> Unit = {
            selectionOne ->  onItemClickSelection(selectionOne)
    }
    private val onItemClickSelectionTwo: (selectionTwo: SelectionFilmsTwoDto) -> Unit = {
            selectionTwo -> onItemClickSelectionTwo(selectionTwo)
    }
    private val onItemClickSeries: (seriesFilm: SeriesFilmsDto) -> Unit = {
            seriesFilm -> onItemClickSeries(seriesFilm)
    }
    private val viewedAdapter = ProfileAllFilmsAdapter(onClickFilm)
    private val topFilmsAllAdapter = TopFilmsAllAdapter(onItemClickTopFilm)
    private val premiersAllAdapter = PremiersFilmsAllAdapter(onItemClick)
    private val popularAllAdapter = PopularFilmsAllAdapter(onItemClickPopularFilm)
    private val selectionOneAllAdapter = SelectionAllAdapter(onItemClickSelection)
    private val selectionTwoAllAdapter = SelectionTwoAllAdapter(onItemClickSelectionTwo)
    private val seriesAllAdapter = SeriesAllAdapter(onItemClickSeries)
    private val staffAdapter = StaffAdapter{ pageDto -> onClickStaff(pageDto) }
    private val similarFilmAdapter = SimilarFilmAdapter{ pageDto -> onClickRelated(pageDto)}
    private val infoActorAdapter = InfoActorAdapter{ pageDto -> onClickFilmActor(pageDto) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentProfileAllFilmsBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val idBundleCollection = arguments?.getInt(COLLECTION_ID)
        val clickViewedCollection = arguments?.getBoolean(CLICK_VIEWED_COLLECTION)
        val clickLookCollection = arguments?.getBoolean(CLICK_LOOK_COLLECTION)
        val clickTopFilmsAll = arguments?.getBoolean(CLICK_TOP_FILMS_ALL)
        val clickPremiersFilmsAll = arguments?.getBoolean(CLICK_PREMIERS_FILMS_ALL)
        val clickPopularFilmsAll = arguments?.getBoolean(CLICK_POPULAR_FILMS_ALL)
        val clickSelectionOneFilmsAll = arguments?.getBoolean(CLICK_SELECTION_ONE_ALL )
        val clickSelectionTwoFilmsAll = arguments?.getBoolean(CLICK_SELECTION_TWO_ALL)
        val clickSeriesFilmsAll = arguments?.getBoolean(CLICK_SERIES_FILMS_ALL)
        val clickAllActor = arguments?.getBoolean(CLICK_ALL_ACTOR_ID)
        val clickAllRelatedFilm = arguments?.getBoolean(CLICK_ALL_RELATED_FILM)
        val clickAllFilmsActor = arguments?.getBoolean(FILM_ACTOR_TRUE)
        val filmsActorBestId = arguments?.getInt(FILM_ID_ACTOR)

        val idBundle = arguments?.getInt(InfoFilmFragment.ID)!!

        binding.recyclerViewed.layoutManager = GridLayoutManager(
            requireContext(), 2, LinearLayoutManager.VERTICAL, false)
        when {
            clickViewedCollection == true -> {
                binding.recyclerViewed.adapter = viewedAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(2)?.films?.let {
                            viewedAdapter.setData(it)
                        }
                    }
                }
            }
            clickLookCollection == true -> {
                binding.recyclerViewed.adapter = viewedAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(1)?.films?.let {
                            viewedAdapter.setData(it)
                        }
                        binding.title.text = getString(R.string.interesting)
                    }
                }
            }
            clickPremiersFilmsAll == true -> {
                binding.recyclerViewed.adapter = premiersAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    premiersAllAdapter.setData(viewModel.getPremiersFilms().subList(0,88))
                    binding.title.text = getString(R.string.premiers)
                }
            }
            clickPopularFilmsAll == true -> {
                binding.recyclerViewed.adapter = popularAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    popularAllAdapter.setData(viewModel.getPopularFilms())
                    binding.title.text = getString(R.string.popular)
                }
            }
            clickTopFilmsAll == true -> {
                binding.recyclerViewed.adapter = topFilmsAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    topFilmsAllAdapter.setData(viewModel.getTopFilms())
                    binding.title.text = getString(R.string.top_250)
                }
            }
            clickSelectionOneFilmsAll == true -> {
                binding.recyclerViewed.adapter = selectionOneAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    selectionOneAllAdapter.setData(viewModel.getSelectionFilms())
                    binding.title.visibility = View.INVISIBLE
                }
            }
            clickSelectionTwoFilmsAll == true -> {
                binding.recyclerViewed.adapter = selectionTwoAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    selectionTwoAllAdapter.setData(viewModel.getSelectionsFilmTwo())
                    binding.title.visibility = View.INVISIBLE
                }
            }
            clickSeriesFilmsAll == true -> {
                binding.recyclerViewed.adapter = seriesAllAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    seriesAllAdapter.setData(viewModel.getSeriesFilms())
                    binding.title.text = getString(R.string.serial)
                }
            }
            clickAllActor == true -> {
                binding.recyclerViewed.adapter = staffAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    staffAdapter.setData(viewModel.staff(idBundle))
                    binding.title.visibility = View.INVISIBLE
                }
            }
            clickAllRelatedFilm == true -> {
                binding.recyclerViewed.adapter = similarFilmAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    similarFilmAdapter.setData(viewModel.similarFilm(idBundle))
                    binding.title.visibility = View.INVISIBLE
                }
            }
            clickAllFilmsActor == true -> {
                binding.recyclerViewed.adapter = infoActorAdapter
                viewLifecycleOwner.lifecycleScope.launch {
                    infoActorAdapter.setData(filmsActorBestId?.let { viewModel.actorFilmBest(it).films }!!)
                    binding.title.visibility = View.INVISIBLE
                }
            }
            else -> {
            viewLifecycleOwner.lifecycleScope.launch {
                binding.recyclerViewed.adapter = viewedAdapter
                viewModel.allCollection.collect { item ->
                    if (idBundleCollection != null) {
                        item.elementAtOrNull(idBundleCollection - 1)?.films?.let {
                            viewedAdapter.setData(it)
                        }
                        binding.title.text = item.elementAtOrNull(idBundleCollection -1)?.collection?.nameCollection
                    }
                }
            }
            }
        }
        with(binding){
            buttonEnd.setOnClickListener {
                findNavController().navigate(R.id.action_profileAllFilmsFragment_to_profileFragment)
            }
        }
    }
    private fun onClickFilm(dataItem: FilmEntity){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }

    private fun onItemClickTopFilm(dataItem: TopFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClick(dataItem: PremiersFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickPopularFilm(dataItem: PopularFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSelection(dataItem: SelectionFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSelectionTwo(dataItem: SelectionFilmsTwoDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSeries(dataItem: SeriesFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(InfoFilmFragment.ID, id)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onClickStaff(dataItem: InfoStaffDto){
        val id = dataItem.staffId
        val bundle = Bundle()
        bundle.putInt(ActorPageFragment.STAFF_ID, id!!)
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_actorPageFragment, bundle)
    }
    private fun onClickRelated(dataItem: SimilarFilmDto){
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(InfoFilmFragment.ID, id)
        }
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    private fun onClickFilmActor(dataItem: ListFilmsActor){
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(InfoFilmFragment.ID, id)
        }
        findNavController().navigate(R.id.action_profileAllFilmsFragment_to_infoFilmFragment, bundle)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}