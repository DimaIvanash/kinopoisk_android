package com.example.cinema.presentation.filmGallery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.cinema.databinding.FragmentFullGalleryBinding


class FullGalleryFragment : Fragment() {
    companion object{
        const val PHOTO = "photo"
    }
    private var _binding: FragmentFullGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFullGalleryBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = arguments?.getString(PHOTO)
        Glide.with(binding.imagePoster)
            .load(bundle)
            .into(binding.imagePoster)
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}