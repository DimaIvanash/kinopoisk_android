package com.example.cinema.presentation.search
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.SearchFilmsSettings
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.data.listItems.ListItemsFilmsKeyword
import com.example.cinema.databinding.FragmentSearchBinding
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.HomePages.homePage.CollectionAdapter
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.ID
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SearchFragment : Fragment() {
    companion object {
        const val IS_SETTINGS_CLICKED = "isSettingsClicked"
        const val YEAR_FROM = "yearFrom"
        const val YEAR_TO = "yearTo"
        const val GENRE = "genre"
        const val COUNTRY = "country"
        const val RATING_WIDTH = "rating_width"
        const val RATING_BY = "rating_by"
        const val SHOW = "show"
        const val SORTED = "sorted"
        const val VISIBLE_FILM = "visible_film"
    }

    private val viewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val connectivityChecker = ConnectivityChecker()
    private val searchKeywordAdapter = SearchKeywordAdapter { pageDto ->
        onClickKeywordFilm(pageDto)
    }
    private val searchSettingsFilmsAdapter = SearchSettingsFilmsAdapter { pageDto ->
        onClickSettingFilm(pageDto)
    }
    private val visibilityAdapter = CollectionAdapter { pageDto ->
        onClickViewed(pageDto)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val settingsClickedBundle = arguments?.getBoolean(IS_SETTINGS_CLICKED) == true
        val yearFromBundle = arguments?.getInt(YEAR_FROM)
        val yearToBundle = arguments?.getInt(YEAR_TO)
        val genreBundle = arguments?.getString(GENRE) ?: ""
        val countryBundle = arguments?.getString(COUNTRY) ?: ""
        val ratingWidthBundle = arguments?.getInt(RATING_WIDTH)
        val ratingByBundle = arguments?.getInt(RATING_BY)
        val typeShow = arguments?.getString(SHOW)
        val sortedBundle = arguments?.getString(SORTED)
        val getVisibleFilm = arguments?.getBoolean(VISIBLE_FILM)
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                binding.recyclerSearchKeyword.adapter = searchKeywordAdapter
                binding.recyclerSearchKeyword.layoutManager = GridLayoutManager(
                    requireContext(), 1, LinearLayoutManager.VERTICAL, false
                )

                if (settingsClickedBundle) {
                    binding.recyclerSettings.adapter =
                        ConcatAdapter(searchSettingsFilmsAdapter, visibilityAdapter)
                    binding.recyclerSettings.layoutManager = GridLayoutManager(
                        requireContext(), 1, LinearLayoutManager.VERTICAL, false
                    )
                    viewLifecycleOwner.lifecycleScope.launch {
                        searchSettingsFilmsAdapter.setData(
                            viewModel.getSearchSettingsFilms(
                                type = typeShow,
                                countries = getCountry(countryBundle),
                                genres = getGenre(genreBundle),
                                yearFrom = yearFromBundle,
                                yearTo = yearToBundle,
                                ratingFrom = ratingWidthBundle,
                                ratingTo = ratingByBundle,
                                order = sortedBundle
                            ).items
                        )

                        if (getVisibleFilm != null && getVisibleFilm == true) {
                            viewModel.allCollection.collect { item ->
                                item.elementAtOrNull(2)?.films.let {
                                    if (it != null) {
                                        visibilityAdapter.setData(it)
                                    }
                                }
                            }
                        } else {
                            visibilityAdapter.setData(emptyList())
                        }
                    }
                    binding.recyclerSearchKeyword.visibility = View.INVISIBLE
                }
                binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    androidx.appcompat.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        viewLifecycleOwner.lifecycleScope.launch {
                            if (!settingsClickedBundle) {
                                val films = viewModel.keyword(newText.toString(), 1).films
                                if (films.isNotEmpty()) {
                                    viewLifecycleOwner.lifecycleScope.launch {
                                        searchKeywordAdapter.setData(films)
                                    }
                                    binding.searchNotFinder.visibility = View.INVISIBLE
                                    binding.recyclerSearchKeyword.visibility = View.VISIBLE
                                } else {
                                    binding.searchNotFinder.visibility = View.VISIBLE
                                    binding.recyclerSearchKeyword.visibility = View.INVISIBLE
                                }
                            }
                        }
                        return true
                    }
                })
            } else {
//                // Интернет недоступен
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Обработка ошибки
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
        with(binding) {
            buttonSettings.setOnClickListener {
                findNavController().navigate(R.id.action_searchFragment_to_searchSettingsFragment)
            }
        }
    }

    private suspend fun getCountry(country: String): Int {
        var countryId = 0
        viewModel.getSearchIdGenreOrCountry().countries.forEach {
            if (it.country!!.contains(country)) {
                countryId = it.id!!
            }
        }
        return countryId
    }

    private suspend fun getGenre(genre: String): Int {
        var genreId = 0
        viewModel.getSearchIdGenreOrCountry().items.forEach {
            if (it.genre.contains(genre)) {
                genreId = it.id!!
            }
        }
        return genreId
    }

    private fun onClickViewed(dataItem: FilmEntity) {
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_searchFragment_to_infoFilmFragment, bundle)
    }

    private fun onClickSettingFilm(dataItem: SearchFilmsSettings) {
        val id = dataItem.id
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(ID, id)
        }
        findNavController().navigate(R.id.action_searchFragment_to_infoFilmFragment, bundle)
    }

    private fun onClickKeywordFilm(dataItem: ListItemsFilmsKeyword) {
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(ID, id)
        }
        findNavController().navigate(R.id.action_searchFragment_to_infoFilmFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}