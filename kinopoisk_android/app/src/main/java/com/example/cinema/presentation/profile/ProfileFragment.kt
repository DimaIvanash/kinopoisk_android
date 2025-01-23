package com.example.cinema.presentation.profile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cinema.R
import com.example.cinema.data.dataBases.CollectionEntity
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.databinding.FragmentProfileBinding
import com.example.cinema.presentation.HomePages.homePage.DialogScreen
import com.example.cinema.presentation.HomePages.homePage.HomePageViewModel
import com.example.cinema.presentation.HomePages.homePage.InfoFilmFragment.Companion.ID
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_LOOK_COLLECTION
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.CLICK_VIEWED_COLLECTION
import com.example.cinema.presentation.profile.ProfileAllFilmsFragment.Companion.COLLECTION_ID
import com.example.cinema.presentation.search.WatchedMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: HomePageViewModel by viewModels()
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val onClickDelete: (collection: CollectionEntity) -> Unit = { collection ->
        onClickDelete(collection)
    }
    private val onClickOpenAll: (collectionAll: CollectionEntity) -> Unit = { collectionAll ->
        onClickOpenAll(collectionAll)
    }
    private val onClickFilm: (clickFilm : FilmEntity) -> Unit = { filmWidthCollection ->
        onClickFilm(filmWidthCollection)
    }
    private val onClickDeleteViewed: (filmsViewed: FilmEntity) -> Unit = { filmsViewed ->
        onClickDeleteViewed(filmsViewed)
    }
    private val onClickDeleteLook: (filmsLook: FilmEntity) -> Unit = {filmsLook ->
        onClickDeleteLook(filmsLook)
    }

    private val collectionAdapter = ProfileCollectionAdapter(onClickDelete, onClickOpenAll)

    private val viewedAdapter = WatchedMoviesAdapter(onClickDeleteViewed, onClickFilm)

    private val lookAdapter = WatchedMoviesAdapter(onClickDeleteLook, onClickFilm)
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){
            recyclerViewed.adapter = viewedAdapter
            recyclerViewed.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)

            recyclerCollection.adapter = collectionAdapter
            recyclerCollection.layoutManager = GridLayoutManager(
               requireContext(), 2, LinearLayoutManager.VERTICAL, false)

            val concatAdapter = ConcatAdapter(lookAdapter)
            recyclerInteresting.adapter = concatAdapter
                recyclerInteresting.layoutManager = LinearLayoutManager(
                requireContext(), LinearLayoutManager.HORIZONTAL, false)

        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCollection.collect{ item ->
                binding.allViewed.text = item.elementAtOrNull(2)?.films?.size.toString()
                item.elementAtOrNull(2)?.films.let {
                    if (it != null) {
                        viewedAdapter.setData(it)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCollection.collect{ item ->
                binding.allInteresting.text = item.elementAtOrNull(1)?.films?.size.toString()
                item.elementAtOrNull(1)?.films.let {
                    if (it != null) {
                        lookAdapter.setData(it)
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.allCollection.collect{ item ->
                collectionAdapter.setData(item)
            }
        }
        with(binding){
            createPlus.setOnClickListener {
                val dialog = DialogScreen().getDialog(requireActivity(), DialogScreen.ADD_COLLECTION_DIALOG, viewModel)
                dialog?.show()
            }
            buttonAllOne.setOnClickListener {
                val onClick = true
                val bundle = Bundle()
                bundle.putBoolean(CLICK_VIEWED_COLLECTION, onClick)
                findNavController().navigate(R.id.action_profileFragment_to_profileAllFilmsFragment, bundle)
            }
            buttonAllTwo.setOnClickListener {
                val onClick = true
                val bundle = Bundle()
                bundle.putBoolean(CLICK_LOOK_COLLECTION, onClick)
                findNavController().navigate(R.id.action_profileFragment_to_profileAllFilmsFragment, bundle)
            }
        }
    }
    private fun onClickDelete(item: CollectionEntity){
        viewModel.deleteCollection(item)
    }
    private fun onClickDeleteViewed(item: FilmEntity){
        item.id.let { viewModel.deleteFilmForCollection(3, it) }
    }
    private fun onClickDeleteLook(item: FilmEntity){
        item.id.let { viewModel.deleteFilmForCollection(2, it) }
    }
    private fun onClickOpenAll(dataItem: CollectionEntity){
        val collectionId = dataItem.collectionId
        val bundle = Bundle()
        if (collectionId != null) {
            bundle.putInt(COLLECTION_ID, collectionId)
        }
        findNavController().navigate(R.id.action_profileFragment_to_profileAllFilmsFragment, bundle)
    }
    private fun onClickFilm(dataItem: FilmEntity){
        val id = dataItem.id
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_profileFragment_to_infoFilmFragment, bundle)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}