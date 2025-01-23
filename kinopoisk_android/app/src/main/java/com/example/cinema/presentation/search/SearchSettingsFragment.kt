package com.example.cinema.presentation.search


import android.annotation.SuppressLint

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.cinema.R
import com.example.cinema.databinding.FragmentSearchSettingsBinding
import com.example.cinema.presentation.search.SearchFragment.Companion.COUNTRY
import com.example.cinema.presentation.search.SearchFragment.Companion.GENRE
import com.example.cinema.presentation.search.SearchFragment.Companion.IS_SETTINGS_CLICKED
import com.example.cinema.presentation.search.SearchFragment.Companion.RATING_BY
import com.example.cinema.presentation.search.SearchFragment.Companion.RATING_WIDTH
import com.example.cinema.presentation.search.SearchFragment.Companion.SHOW
import com.example.cinema.presentation.search.SearchFragment.Companion.SORTED
import com.example.cinema.presentation.search.SearchFragment.Companion.VISIBLE_FILM
import com.example.cinema.presentation.search.SearchFragment.Companion.YEAR_FROM
import com.example.cinema.presentation.search.SearchFragment.Companion.YEAR_TO
import com.google.android.material.slider.RangeSlider
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class SearchSettingsFragment : androidx.fragment.app.Fragment() {
    companion object{
        const val PREFERENCES_GENRE = "prefs_genre"
        const val KEY_GENRE = "KEY_GENRE"
        const val KEY_COUNTRY = "KEY_COUNTRY"
        const val KEY_YEAR_FROM = "KEY_YEAR_FROM"
        const val KEY_YEAR_TO = "KEY_YEAR_TO"
        const val KEY_RATING_WIDTH = "RATING_WIDTH"
        const val KEY_RATING_BY = "RATING_BY"
        const val KEY_SHOW = "KEY_SHOW"
        const val KEY_VISIBLE = "KEY_VISIBLE"
    }
    private var _binding : FragmentSearchSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchSettingsBinding.inflate(layoutInflater)
        return binding.root
    }
    @SuppressLint("SetTextI18n", "CommitPrefEdits")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefs = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE)

        val keyGenresPref = prefs.getString(KEY_GENRE, "")
        binding.textGenre.text = keyGenresPref

        val keyCountryPref = prefs.getString(KEY_COUNTRY,"")
        binding.textCountry.text = keyCountryPref

        val keyPrefYearFrom = prefs.getInt(KEY_YEAR_FROM, 2023)
        val keePrefYearBy = prefs.getInt(KEY_YEAR_TO, 2024)

        val keyRatingWidth = prefs.getInt(KEY_RATING_WIDTH,0)
        val keyRatingBy = prefs.getInt(KEY_RATING_BY, 10)

        val valuesRating = getTrackRating()

        val searchAll = prefs.getString(KEY_SHOW,"")

        var visibleFilm = prefs.getBoolean(KEY_VISIBLE, false)
        onItemClick(
            keyPrefYearFrom,
            keePrefYearBy,
            valueOne = valuesRating[0],
            valueTwo = valuesRating[1],
            keyCountryPref = keyCountryPref.toString(),
            keyGenresPref = keyGenresPref.toString(),
            searchAll = searchAll.toString(),
            visibleFilm = visibleFilm
        )
        binding.textYearWidth.text = getString(R.string.width_value, keyPrefYearFrom.toString() )
        binding.textYearBy.text = getString(R.string.by_value, keePrefYearBy.toString())

        binding.textRatingWidth.text = getString(R.string.width_value, keyRatingWidth.toString()) + getString(R.string.by_value, keyRatingBy.toString())
        binding.hide.setOnClickListener {
            when{
                binding.hide.isChecked -> {
                    visibleFilm = true
                    binding.hide.setBackgroundResource(R.drawable.visibility_black)
                }
                !binding.hide.isChecked -> {
                    visibleFilm = false
                    binding.hide.setBackgroundResource(R.drawable.visibility_off_black)
                }
            }
            val bundle = Bundle()
            bundle.putBoolean(VISIBLE_FILM, visibleFilm)
            val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE).edit()
            editor.putBoolean(KEY_VISIBLE, visibleFilm).apply()
        }
        binding.buttonYear.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsFragment_to_searchSettingYearFragment)
        }
        binding.buttonGenre.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsFragment_to_searchSettingGenreFragment)
        }
        binding.buttonCountry.setOnClickListener {
            findNavController().navigate(R.id.action_searchSettingsFragment_to_searchSettingsCountryFragment)
        }
    }
    @SuppressLint("CommitPrefEdits")
    private fun onItemClick(keyPrefYearFrom: Int, keyPrefYearBy: Int,
                            valueOne: Int, valueTwo: Int, keyCountryPref: String, keyGenresPref: String, searchAll: String, visibleFilm: Boolean){
        val isSettingsClicked = true
        val bundle = Bundle()
        bundle.putBoolean(IS_SETTINGS_CLICKED, isSettingsClicked)
        bundle.putString(COUNTRY, keyCountryPref )
        bundle.putString(GENRE, keyGenresPref)
        bundle.putInt(YEAR_FROM, keyPrefYearFrom )
        bundle.putInt(YEAR_TO, keyPrefYearBy)
        bundle.putInt(RATING_WIDTH, valueOne )
        bundle.putInt(RATING_BY, valueTwo)
        bundle.putString(SHOW, searchAll)
        bundle.putBoolean(VISIBLE_FILM, visibleFilm)
        binding.outlineInt.setOnClickListener {
            when{
                binding.buttonAll.isChecked -> {
                    bundle.putString(SHOW, "ALL")
                    saveSelectedValue(searchAll = "ALL")
                }
                binding.buttonFilms.isChecked -> {
                    bundle.putString(SHOW, "FILM")
                    saveSelectedValue(searchAll = "FILM")
                }
                binding.buttonSerial.isChecked -> {
                    bundle.putString(SHOW, "TV_SERIES")
                    saveSelectedValue(searchAll = "TV_SERIES")
                }
            }
            when{
                binding.buttonDate.isChecked -> {
                    bundle.putString(SORTED, "YEAR")
                }
                binding.buttonPopular.isChecked -> {
                    bundle.putString(SORTED, "NUM_VOTE")
                }
                binding.buttonRating.isChecked -> {
                    bundle.putString(SORTED, "RATING")
                }
            }
            findNavController().navigate(R.id.action_searchSettingsFragment_to_searchFragment, bundle)
        }
    }
    private fun saveSelectedValue(searchAll: String) {
        val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE).edit()
        editor.putString(KEY_SHOW, searchAll).apply()
    }
    private fun getTrackRating(): Array<Int> {
        var valueOne = 0
        var valueTwo = 10
        binding.rangeSlider.addOnSliderTouchListener(object : RangeSlider.OnSliderTouchListener {
            @SuppressLint("SetTextI18n")
            override fun onStartTrackingTouch(slider: RangeSlider) {
                valueOne = slider.values[0].toInt()
                valueTwo = slider.values[1].toInt()
                binding.valueOne.text = valueOne.toString()
                binding.valueTwo.text = valueTwo.toString()
                binding.textRatingWidth.text = getString(R.string.width_value, valueOne.toString()) + getString(R.string.by_value, valueTwo.toString())
            }
            @SuppressLint("SetTextI18n")
            override fun onStopTrackingTouch(slider: RangeSlider) {
                valueOne = slider.values[0].toInt()
                valueTwo = slider.values[1].toInt()
                binding.valueOne.text = valueOne.toString()
                binding.valueTwo.text = valueTwo.toString()
                val editor = requireActivity().getSharedPreferences(PREFERENCES_GENRE, MODE_PRIVATE).edit()
                editor.putInt(KEY_RATING_WIDTH, valueOne).apply()
                editor.putInt(KEY_RATING_BY, valueTwo).apply()
                binding.textRatingWidth.text = getString(R.string.width_value, valueOne.toString()) + getString(R.string.by_value, valueTwo.toString())
            }
        })
        return arrayOf(valueOne, valueTwo)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


