package com.example.cinema.presentation.HomePages.homePage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.ID
import com.example.cinema.R
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.data.PremiersFilmsDto
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.data.SelectionFilmsTwoDto
import com.example.cinema.data.SeriesFilmsDto
import com.example.cinema.data.TopFilmsDto
import com.example.cinema.data.dataBases.CollectionEntity
import com.example.cinema.databinding.FragmentHomePageBinding
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.CLICK_SERIES_FILMS
import com.example.cinema.presentation.HomePages.homePage.popularFilm.PopularFilmAdapter
import com.example.cinema.presentation.HomePages.homePage.premiers.PremiersFilmAdapter
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_POPULAR_FILMS_ALL
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_PREMIERS_FILMS_ALL
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_SELECTION_ONE_ALL
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_SELECTION_TWO_ALL
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_SERIES_FILMS_ALL
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_TOP_FILMS_ALL
import com.example.cinema.presentation.HomePages.homePage.selectionFilm.SelectionAdapter
import com.example.cinema.presentation.HomePages.homePage.selectionFilmTwo.SelectionAdapterTwo
import com.example.cinema.presentation.HomePages.homePage.seriesFilm.SeriesAdapter
import com.example.cinema.presentation.State
import com.example.cinema.presentation.HomePages.homePage.topFilm.TopFilmAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class HomePageFragment : Fragment() {
    private val viewModel: HomePageViewModel by viewModels()
    private var _binding : FragmentHomePageBinding? = null
    private val connectivityChecker = ConnectivityChecker()
    private val binding get() = _binding!!
    private val onItemClickTopFilm: (topFilm: TopFilmsDto) -> Unit = {
        topFilm -> onItemClickTopFilm(topFilm)
    }
    private val onClickTopFilmsAll: (topFilmsAll: TopFilmsDto) -> Unit = {
        onClickTopFilmsAll()
    }
    private val onItemClick: (premiersFilm: PremiersFilmsDto) -> Unit = {
            premiersFilm -> onItemClick(premiersFilm)
    }
    private val onClickPremiersFilmsAll: (premiersAll: PremiersFilmsDto) -> Unit = {
    onClickPremiersFilmsAll()
    }
    private val onItemClickPopularFilm: (popularFilm: PopularFilmsDto) -> Unit = {
        popularFilm -> onItemClickPopularFilm(popularFilm)
    }
    private val onClickPopularFilmsAll: (popularAll: PopularFilmsDto) -> Unit = {
        onClickPopularFilmsAll()
    }
    private val onItemClickSelection: (selectionOne: SelectionFilmsDto) -> Unit = {
        selectionOne ->  onItemClickSelection(selectionOne)
    }
    private val onClickSelectionOneFilmsAll: (selectionOneAll: SelectionFilmsDto) -> Unit = {
        onClickSelectionOneFilmsAll()
    }
    private val onItemClickSelectionTwo: (selectionTwo: SelectionFilmsTwoDto) -> Unit = {
        selectionTwo -> onItemClickSelectionTwo(selectionTwo)
    }
    private val onClickSelectionTwoFilmsAll: (selectionTwoAll: SelectionFilmsTwoDto) -> Unit = {
        onClickSelectionTwoFilmsAll()
    }
    private val onItemClickSeries: (seriesFilm: SeriesFilmsDto) -> Unit = {
        seriesFilm -> onItemClickSeries(seriesFilm)
    }
    private val onClickSeriesFilmsAll: (seriesAll: SeriesFilmsDto) -> Unit = {
        onClickSeriesFilmsAll()
    }
    private val premiersAdapter = PremiersFilmAdapter(onItemClick,onClickPremiersFilmsAll)
    private val topFilmAdapter = TopFilmAdapter(onItemClickTopFilm, onClickTopFilmsAll)
    private val popularFilmAdapter = PopularFilmAdapter (onItemClickPopularFilm, onClickPopularFilmsAll)
    private val selectionFilmAdapter = SelectionAdapter(onItemClickSelection, onClickSelectionOneFilmsAll)
    private val selectionFilmAdapterTwo = SelectionAdapterTwo(onItemClickSelectionTwo, onClickSelectionTwoFilmsAll)
    private val seriesFilmAdapter = SeriesAdapter(onItemClickSeries, onClickSeriesFilmsAll )

    private val likeFilm = CollectionEntity(1,"Любимые")
    private val bookmarkFilm = CollectionEntity(2, "Хочу посмотреть")
    private val visibilityFilm = CollectionEntity(3,"Просмотренные фильмы")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomePageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                lifecycleScope.launch {
                    viewModel.addCollection(likeFilm)
                    viewModel.addCollection(bookmarkFilm)
                    viewModel.addCollection(visibilityFilm)
                    binding.recyclerPremier.adapter = premiersAdapter
                    binding.recyclerPremier.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )
                    binding.recyclerPopular.adapter = popularFilmAdapter
                    binding.recyclerPopular.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )
                    binding.recyclerTopFilm.adapter = topFilmAdapter
                    binding.recyclerTopFilm.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )
                    binding.recyclerSerialFilm.adapter = seriesFilmAdapter
                    binding.recyclerSerialFilm.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )
                    binding.recyclerCountryFilm.adapter = selectionFilmAdapter
                    binding.recyclerCountryFilm.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )
                    binding.recyclerCountryTwoFilm.adapter = selectionFilmAdapterTwo
                    binding.recyclerCountryTwoFilm.layoutManager = LinearLayoutManager(
                        requireContext(), GridLayoutManager.HORIZONTAL, false
                    )

                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect{item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            premiersAdapter.setData(viewModel.getPremiersFilms().subList(0, 20), films)
                        }
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect{ item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            popularFilmAdapter.setData(viewModel.getPopularFilms().subList(0, 20), films)
                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            topFilmAdapter.setData(viewModel.getTopFilms().subList(0, 20), films)

                        }
                    }
                }

                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            selectionFilmAdapter.setData(viewModel.getSelectionFilms().subList(0, 20), films)

                        }
                        when(selectionFilmAdapter.getGenre()){
                            getString(R.string.drama).lowercase(Locale.ROOT) -> binding.titleCountryFilm.text = getString(R.string.dramas)
                            getString(R.string.thriller).lowercase(Locale.ROOT) -> binding.titleCountryFilm.text = getString(R.string.thrillers)
                            getString(R.string.melodrama).lowercase(Locale.ROOT) -> binding.titleCountryFilm.text = getString(R.string.melodrama)
                            getString(R.string.crime).lowercase(Locale.ROOT) -> binding.titleCountryFilm.text = getString(R.string.crime)
                        }
                        when(selectionFilmAdapter.getCountries()){
                            getString(R.string.usa) -> binding.titleLine.text = getString(R.string.usa)
                            getString(R.string.russia) -> binding.titleLine.text = getString(R.string.of_russia)
                            getString(R.string.france) -> binding.titleLine.text = getString(R.string.of_france)
                            getString(R.string.switzerland) -> binding.titleLine.text = getString(R.string.of_switzerland)
                            getString(R.string.poland) -> binding.titleLine.text = getString(R.string.of_poland)
                        }
                    }

                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            selectionFilmAdapterTwo.setData(viewModel.getSelectionsFilmTwo().subList(0, 20), films)
                        }
                        when(selectionFilmAdapterTwo.getGenre()){
                            getString(R.string.drama).lowercase(Locale.ROOT) -> binding.titleCountryTwoFilm.text = getString(R.string.dramas)
                            getString(R.string.thriller).lowercase(Locale.ROOT) -> binding.titleCountryTwoFilm.text = getString(R.string.thrillers)
                            getString(R.string.melodrama).lowercase(Locale.ROOT) -> binding.titleCountryTwoFilm.text = getString(R.string.melodrama)
                            getString(R.string.crime).lowercase(Locale.ROOT) -> binding.titleCountryTwoFilm.text = getString(R.string.crime)
                        }
                        when(selectionFilmAdapterTwo.getCountries()){
                            getString(R.string.usa) -> binding.titleLineTwo.text = getString(R.string.usa)
                            getString(R.string.russia) -> binding.titleLineTwo.text = getString(R.string.of_russia)
                            getString(R.string.france) -> binding.titleLineTwo.text = getString(R.string.of_france)
                            getString(R.string.switzerland) -> binding.titleLineTwo.text = getString(R.string.of_switzerland)
                            getString(R.string.poland) -> binding.titleLineTwo.text = getString(R.string.of_poland)
                            getString(R.string.great_britain) -> binding.titleLineTwo.text = getString(R.string.of_great_britain)
                        }
                    }

                }
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.allCollection.collect { item ->
                        item.elementAtOrNull(2)?.films?.let { films ->
                            seriesFilmAdapter.setData(viewModel.getSeriesFilms().subList(0, 20), films)
                        }
                    }
                }
                with(binding){
                    allPremier.setOnClickListener{ onClickPremiersFilmsAll() }
                    allPopular.setOnClickListener { onClickPopularFilmsAll() }
                    allTopFilm.setOnClickListener { onClickTopFilmsAll() }
                    allCountryFilm.setOnClickListener { onClickSelectionOneFilmsAll() }
                    allCountryTwoFilm.setOnClickListener { onClickSelectionTwoFilmsAll() }
                    allSerialFilm.setOnClickListener { onClickSeriesFilmsAll() }
                }
                loadingVisible()
            }else {
//                // Интернет недоступен
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        }catch (e: Exception) {
            // Обработка ошибки
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun onItemClick(dataItem: PremiersFilmsDto){
         val id = dataItem.id
         val bundle = Bundle()
         bundle.putInt(ID, id)
         findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickPopularFilm(dataItem: PopularFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickTopFilm(dataItem: TopFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSelection(dataItem: SelectionFilmsDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSelectionTwo(dataItem: SelectionFilmsTwoDto){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
    private fun onItemClickSeries(dataItem: SeriesFilmsDto){
        val id = dataItem.id
        val visibleSeries = true
        val bundle = Bundle()
        bundle.putInt(ID, id)
        bundle.putBoolean(CLICK_SERIES_FILMS, visibleSeries )
        findNavController().navigate(R.id.action_homePageFragment_to_infoFilmFragment, bundle)
    }
   private fun onClickTopFilmsAll(){
       val onClick = true
       val bundle = Bundle()
       bundle.putBoolean(CLICK_TOP_FILMS_ALL, onClick)
       findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
   }
    private fun onClickPopularFilmsAll(){
        val onClick = true
        val bundle = Bundle()
        bundle.putBoolean(CLICK_POPULAR_FILMS_ALL, onClick)
        findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun onClickPremiersFilmsAll(){
        val onClick = true
        val bundle = Bundle()
        bundle.putBoolean(CLICK_PREMIERS_FILMS_ALL, onClick)
        findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun onClickSelectionOneFilmsAll(){
        val onClick = true
        val bundle = Bundle()
        bundle.putBoolean(CLICK_SELECTION_ONE_ALL, onClick)
        findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun onClickSelectionTwoFilmsAll(){
        val onClick = true
        val bundle = Bundle()
        bundle.putBoolean(CLICK_SELECTION_TWO_ALL, onClick)
        findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun onClickSeriesFilmsAll(){
        val onClick = true
        val bundle = Bundle()
        bundle.putBoolean(CLICK_SERIES_FILMS_ALL, onClick)
        findNavController().navigate(R.id.action_homePageFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun loadingVisible() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{state ->
                when(state){
                    State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.scrollView.visibility = View.INVISIBLE
                    }
                    State.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.scrollView.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


