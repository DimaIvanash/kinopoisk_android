package com.example.cinema.presentation.filmGallery

import androidx.lifecycle.ViewModel
import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.domain.GetPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class GalleryViewModel @Inject constructor(private val useCase: GetPageUseCase): ViewModel() {

    suspend fun gallery(id: Int): List<FilmGalleryDto>{
        return useCase.executeFilmGallery(id, page = 1, type = "STILL" )
    }
    suspend fun galleryShooting(id: Int): List<FilmGalleryDto>{
        return useCase.executeFilmGallery(id, page = 1, type = "SHOOTING" )
    }
    suspend fun galleryPoster(id: Int): List<FilmGalleryDto>{
        return useCase.executeFilmGallery(id, page = 1, type = "POSTER" )
    }
}