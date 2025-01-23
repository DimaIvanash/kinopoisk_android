package com.example.cinema.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.data.InfoDto
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.data.StaffJobFilmDto
import com.example.cinema.data.dataBases.CollectionEntity
import com.example.cinema.data.dataBases.CollectionListFilms
import com.example.cinema.data.dataBases.Dao
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.domain.GetPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoFilmViewModel @Inject constructor(private val useCase: GetPageUseCase, private val collectionDao: Dao): ViewModel() {
    suspend fun apiInfo(idBundle: Int): InfoDto{
        return useCase.executeInfoFilm(idBundle)
    }
//    suspend fun staff(filmId: Int): List<InfoStaffDto>{
//        return useCase.executeInfoStaff(filmId)
//    }
    suspend fun staffJobFilm(filmId: Int): List<StaffJobFilmDto>{
        return useCase.executeStaffJobFilm(filmId)
    }
    suspend fun gallery(id: Int): List<FilmGalleryDto>{
        return useCase.executeFilmGallery(id, page = 1, type = "STILL" )
    }
    suspend fun similarFilm(id: Int): List<SimilarFilmDto>{
        return useCase.executeSimilarFilm(id)
    }

    val allCollection = this.collectionDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = emptyList()  //пустой список
        )
    fun addFilm(table: FilmEntity){
        viewModelScope.launch {
            collectionDao.insert(table)
        }
    }
    fun addFilmForCollection(collectionId: Int, filmId: Int){
        viewModelScope.launch {
            collectionDao.insertFilmForCollection(CollectionListFilms(collectionId, filmId))
        }
    }
    fun deleteFilmForCollection(collectionId: Int, filmId: Int){
        viewModelScope.launch {
            collectionDao.deleteFilmForCollection(CollectionListFilms(collectionId, filmId))
        }
    }
    fun addCollection(collection: CollectionEntity){
        viewModelScope.launch {
            collectionDao.insertCollection(collection)
        }
    }
    fun deleteCollection(collection: CollectionEntity){
        viewModelScope.launch {
            collectionDao.deleteCollection(collection)
        }
    }
}



