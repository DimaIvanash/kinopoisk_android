package com.example.cinema.presentation.search


import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSearchSettingYearBinding
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.KEY_YEAR_FROM
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.KEY_YEAR_TO
import com.example.cinema.presentation.search.SearchSettingsFragment.Companion.PREFERENCES_GENRE

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchSettingYearFragment : Fragment() {

//    private val viewModel: SearchSettingYearViewModel by viewModels()
    private var _binding : FragmentSearchSettingYearBinding? = null
    private val binding get() = _binding!!

    private var yearFromDate = 1999
    private var yearToDate = 2000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchSettingYearBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerDate.layoutManager = GridLayoutManager(
            requireContext(), 4, LinearLayoutManager.HORIZONTAL, false)

        binding.recyclerDateTwo.layoutManager = GridLayoutManager(
            requireContext(), 4, LinearLayoutManager.HORIZONTAL, false)

        val dateFrom: List<Int> = (1980..2024).map { it }
        val dateTo: List<Int> = (1980..2024).map { it }

        val dateAdapterFrom = DateAdapterFrom(dateFrom) {
            yearFrom -> onItemClickFrom(yearFrom)
            onItemClick()
        }
        val dateAdapterTo = DateAdapterTo(dateTo) {
            yearTo -> onItemClickTo(yearTo)
            onItemClick()
        }
        onItemClick()

        binding.recyclerDate.adapter = dateAdapterFrom
        binding.recyclerDateTwo.adapter = dateAdapterTo

        val layoutManagerFrom = binding.recyclerDate.layoutManager as LinearLayoutManager
        val layoutManagerTo = binding.recyclerDateTwo.layoutManager as LinearLayoutManager

        binding.buttonNextOne.setOnClickListener {
            if (layoutManagerFrom.findLastCompletelyVisibleItemPosition() < (dateAdapterFrom.itemCount -1)){
                layoutManagerFrom.scrollToPosition(layoutManagerFrom.findLastCompletelyVisibleItemPosition() + 1)
            }
        }
        binding.buttonDownOne.setOnClickListener {
            if (layoutManagerFrom.findFirstCompletelyVisibleItemPosition() < (dateAdapterFrom.itemCount -1)){
                layoutManagerFrom.scrollToPosition(layoutManagerFrom.findFirstCompletelyVisibleItemPosition() - 1)
            }
        }

        binding.buttonNextTwo.setOnClickListener {
            if (layoutManagerTo.findLastCompletelyVisibleItemPosition() < (dateAdapterFrom.itemCount -1)){
                layoutManagerTo.scrollToPosition(layoutManagerTo.findLastCompletelyVisibleItemPosition() + 1)
            }
        }
        binding.buttonDownTwo.setOnClickListener {
            if (layoutManagerTo.findFirstCompletelyVisibleItemPosition() < (dateAdapterFrom.itemCount -1)){
                layoutManagerTo.scrollToPosition(layoutManagerTo.findFirstCompletelyVisibleItemPosition() - 1)
            }
        }
    }

    private fun onItemClickFrom(yearFrom: Int) {
        yearFromDate = yearFrom
        binding.textDate.text = yearFrom.toString()

    }
    private fun onItemClickTo(yearTo: Int) {
        yearToDate = yearTo
        binding.textDateTwo.text = yearTo.toString()
    }
    private fun onItemClick() {
        val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE).edit()
        editor.putInt(KEY_YEAR_FROM, yearFromDate)
        editor.putInt(KEY_YEAR_TO, yearToDate)
        editor.apply()
        binding.buttonChoose.setOnClickListener{
            findNavController().navigate(R.id.action_searchSettingYearFragment_to_searchSettingsFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}


