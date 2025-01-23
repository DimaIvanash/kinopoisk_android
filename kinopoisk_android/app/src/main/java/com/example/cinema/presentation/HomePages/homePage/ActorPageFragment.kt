package com.example.cinema.presentation.HomePages.homePage

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
import com.bumptech.glide.Glide
import com.example.cinema.R
import com.example.cinema.data.listItems.ListFilmsActor
import com.example.cinema.databinding.FragmentActorPageBinding
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.HomePages.homePage.FilmographyFragment.Companion.ACTOR_ID
import com.example.cinema.presentation.State
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.FILM_ACTOR_TRUE
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.FILM_ID_ACTOR
import com.example.cinema.presentation.staff.InfoActorAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActorPageFragment : Fragment() {

    companion object {
        const val STAFF_ID = "staffId"
    }

    private val viewModel: HomePageViewModel by viewModels()
    private var _binding: FragmentActorPageBinding? = null
    private val binding get() = _binding!!
    private val infoActorAdapter = InfoActorAdapter { pageDto -> onClickFilm(pageDto) }
    private val connectivityChecker = ConnectivityChecker()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentActorPageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                val idBundle = arguments?.getInt(STAFF_ID)!!

                val bundleActorId = Bundle()
                bundleActorId.putInt(ACTOR_ID, idBundle)
                viewLifecycleOwner.lifecycleScope.launch {
                    infoActorAdapter.setData(viewModel.actorFilmBest(idBundle).films!!)
                    Glide.with(binding.imageActorPage)
                        .load(viewModel.actorFilmBestId(idBundle).posterUrl ?: "")
                        .centerCrop()
                        .into(binding.imageActorPage)
                    binding.textName.text = viewModel.actorFilmBestId(idBundle).nameRu ?: ""
                    binding.textDescr.text = viewModel.actorFilmBestId(idBundle).description ?: ""
                    binding.textView2.text = getString(
                        R.string.count_films,
                        viewModel.actorFilmBest(idBundle).films!!.size.toString()
                    )
                }
                binding.recyclerActorPage.adapter = infoActorAdapter
                binding.recyclerActorPage.layoutManager = GridLayoutManager(
                    requireContext(), 1, LinearLayoutManager.HORIZONTAL, false
                )
                binding.allFilmography.setOnClickListener {
                    findNavController().navigate(
                        R.id.action_actorPageFragment_to_filmographyFragment,
                        bundleActorId
                    )
                }
                binding.outlineInt.setOnClickListener {
                    findNavController().navigate(R.id.action_actorPageFragment_to_filmographyFragment)
                }
                binding.bestAll.setOnClickListener {
                    viewLifecycleOwner.lifecycleScope.launch {
                        val onClick = true
                        val bundle = Bundle()
                        bundle.putInt(FILM_ID_ACTOR, idBundle)
                        bundle.putBoolean(FILM_ACTOR_TRUE, onClick)
                        findNavController().navigate(
                            R.id.action_actorPageFragment_to_profileAllFilmsFragment,
                            bundle
                        )
                    }
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
    private fun onClickFilm(dataItem : ListFilmsActor){
        val id = dataItem.filmId
        val bundle = Bundle()
        if (id != null) {
            bundle.putInt(InfoFilmFragment.ID, id)
        }
        findNavController().navigate(R.id.action_actorPageFragment_to_infoFilmFragment, bundle)
    }
    private fun loadingVisible() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect{state ->
                when(state){
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