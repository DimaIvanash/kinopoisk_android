package com.example.cinema.presentation.filmGallery


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
import com.example.cinema.data.FilmGalleryDto

import com.example.cinema.databinding.FragmentGalleryBinding
import com.example.cinema.presentation.ConnectivityChecker
import com.example.cinema.presentation.filmGallery.FullGalleryFragment.Companion.PHOTO

import com.example.cinema.presentation.staff.GalleryAdapter
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.coroutines.launch

@AndroidEntryPoint
class GalleryFragment : Fragment() {
    companion object {
        const val GALLERY = "id"
    }

    private val viewModel: GalleryViewModel by viewModels()
    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!
    private val connectivityChecker = ConnectivityChecker()
    private val galleryAdapter = GalleryAdapter { pageDto -> onClick(pageDto) }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idBundle = arguments?.getInt(GALLERY)!!
        try {
            if (connectivityChecker.isInternetAvailable(requireContext())) {
                binding.recyclerGallery.adapter = galleryAdapter
                binding.recyclerGallery.layoutManager = GridLayoutManager(
                    requireContext(),
                    2,
                    LinearLayoutManager.VERTICAL,
                    false
                ).also {
                    it.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position % 3 == 0) {
                                2
                            } else {
                                1
                            }
                        }
                    }
                }
                viewLifecycleOwner.lifecycleScope.launch {
                    with(binding) {
                        chip1.text =
                            getString(R.string.frames, viewModel.gallery(idBundle).size.toString())
                        chip2.text = getString(
                            R.string.from_filming,
                            viewModel.galleryShooting(idBundle).size.toString()
                        )
                        chip3.text = getString(
                            R.string.posters,
                            viewModel.galleryPoster(idBundle).size.toString()
                        )
                    }
                }
                with(binding) {
                    chip1.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            galleryAdapter.setData(viewModel.gallery(idBundle))
                        }
                    }
                    chip2.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            galleryAdapter.setData(viewModel.galleryShooting(idBundle))
                        }
                    }
                    chip3.setOnClickListener {
                        viewLifecycleOwner.lifecycleScope.launch {
                            galleryAdapter.setData(viewModel.galleryPoster(idBundle))
                        }
                    }
                }
            } else {
//                // Интернет недоступен
                Toast.makeText(requireContext(), "Нет интернета", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            // Обработка ошибки
            Toast.makeText(requireContext(), "Ошибка: ${e.message}", Toast.LENGTH_SHORT).show()
        }

    }

    private fun onClick(dataItem: FilmGalleryDto) {
        val photo = dataItem.imageUrl
        val bundle = Bundle()
        bundle.putString(PHOTO, photo)
        findNavController().navigate(R.id.action_galleryFragment_to_fullGalleryFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

