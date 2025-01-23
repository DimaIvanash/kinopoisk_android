package com.example.cinema.presentation.search

import android.content.Context
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
import com.example.cinema.data.listItems.ListCountry
import com.example.cinema.databinding.FragmentSearchSettingsCountryBinding
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.KEY_COUNTRY
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.PREFERENCES_GENRE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchSettingsCountryFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()
    private var _binding : FragmentSearchSettingsCountryBinding? = null
    private val binding get() = _binding!!

    private val countryAdapter = CountryAdapter{
            country -> onItemClick(country)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsCountryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerSearchCountry.adapter = countryAdapter
        binding.recyclerSearchCountry.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                viewLifecycleOwner.lifecycleScope.launch{
                    countryAdapter.setData(viewModel.getGenres(newText.toString()).countries.filter {
                        it.country!!.startsWith(newText.toString(), true)
                    }
                    )
                }
                return true
            }
        })
    }
    private fun onItemClick(countryData: ListCountry){
        val country = countryData.country
        val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_COUNTRY, country).apply()

        binding.outlineInt.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsCountryFragment_to_searchSettingsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}