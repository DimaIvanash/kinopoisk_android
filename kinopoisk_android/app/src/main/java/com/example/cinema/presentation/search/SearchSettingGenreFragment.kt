package com.example.cinema.presentation.search

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.listItems.ListGenres
import com.example.cinema.databinding.FragmentSearchSettingGenreBinding

import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.KEY_GENRE
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.PREFERENCES_GENRE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchSettingGenreFragment : Fragment() {


    private val viewModel: SearchViewModel by viewModels()
    private var _binding : FragmentSearchSettingGenreBinding? = null
    private val binding get() = _binding!!

    private val genresAdapter = GenresAdapter{
            genre -> onItemClick(genre)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchSettingGenreBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerSearchGenre.adapter = genresAdapter
        binding.recyclerSearchGenre.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false

            }

            override fun onQueryTextChange(newText: String?): Boolean {

                viewLifecycleOwner.lifecycleScope.launch{
                    genresAdapter.setData(viewModel.getGenres(newText.toString()).items.filter {
                        it.genre.startsWith(newText.toString(), true)
                    }
                    )
                }

                return true
            }

        })

    }

    private fun onItemClick(genreData: ListGenres){
        val genre = genreData.genre
        val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE).edit()
        editor.putString(KEY_GENRE, genre)
        editor.apply()

        binding.outlineInt.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingGenreFragment_to_searchSettingsFragment)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}