package com.example.cinema.presentation.HomePages.homePage

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.listItems.ListFilmsActor
import com.example.cinema.databinding.FragmentFilmographyBinding
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.ID
import com.example.cinema.presentation.State
import com.example.cinema.presentation.staff.FilmographyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmographyFragment : Fragment() {
    companion object {
        const val ACTOR_ID = "personId"
    }
    private val viewModel: HomePageViewModel by viewModels()
    private var _binding: FragmentFilmographyBinding? = null
    private val connectivityChecker = ConnectivityChecker()
    private val binding get() = _binding!!
    private val filmographyAdapter = FilmographyAdapter { pageDto -> onClickFilm(pageDto) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmographyBinding.inflate(layoutInflater)
        return binding.root
    }
    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                val idBundle = arguments?.getInt(ACTOR_ID)!!
                viewLifecycleOwner.lifecycleScope.launch {
                    with(binding) {
                        recyclerFilmography.adapter = filmographyAdapter
                        recyclerFilmography.layoutManager = GridLayoutManager(
                            requireContext(), 1, LinearLayoutManager.VERTICAL, false
                        )
                        textName.text = viewModel.actorFilmBestId(idBundle).nameRu ?: ""
                        chip1.text = getString(
                            R.string.actor,
                            viewModel.actorFilmBest(idBundle).films!!.filter {
                                it.professionKey == "ACTOR"
                            }.size.toString()
                        )
                        chip2.text = getString(
                            R.string.actor,
                            viewModel.actorFilmBest(idBundle).films!!.filter {
                                it.professionKey == "PRODUCER"
                            }.size.toString()
                        )
                        chip3.text = getString(
                            R.string.actor,
                            viewModel.actorFilmBest(idBundle).films!!.filter {
                                it.professionKey == "DIRECTOR"
                            }.size.toString()
                        )
                        chip1.setOnClickListener {
                            viewLifecycleOwner.lifecycleScope.launch {
                                filmographyAdapter.setData(viewModel.actorFilmBest(idBundle).films!!.filter {
                                    it.professionKey == "ACTOR"
                                })
                            }
                        }
                        chip2.setOnClickListener {
                            viewLifecycleOwner.lifecycleScope.launch {
                                filmographyAdapter.setData(viewModel.produsserFilmographi(idBundle).films!!.filter {
                                    it.professionKey == "PRODUCER"
                                })
                            }
                        }
                        chip3.setOnClickListener {
                            viewLifecycleOwner.lifecycleScope.launch {
                                filmographyAdapter.setData(viewModel.directorFilmographi(idBundle).films!!.filter {
                                    it.professionKey == "DIRECTOR"
                                })
                            }
                        }
                    }
                }
                binding.outlineInt.setOnClickListener {
                    findNavController().navigate(R.id.action_filmographyFragment_to_actorPageFragment)
                }
                loadingVisible()
            } else {
//                // Интернет недоступен
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Обработка ошибки
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
    private fun onClickFilm(dataItem: ListFilmsActor) {
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(ID, id)
        }
        findNavController().navigate(R.id.action_filmographyFragment_to_infoFilmFragment, bundle)
    }
    private fun loadingVisible() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    State.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.container.visibility = View.INVISIBLE
                    }
                    State.Success -> {
                        binding.progressBar.visibility = View.INVISIBLE
                        binding.container.visibility = View.VISIBLE
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