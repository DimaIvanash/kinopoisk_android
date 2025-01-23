package com.example.cinema.presentation.HomePages.homePage


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.data.InfoActorDto
import com.example.cinema.data.InfoDto
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.data.PremiersFilmsDto
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.data.SelectionFilmsTwoDto
import com.example.cinema.data.SeriesFilmsDto
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.data.StaffJobFilmDto
import com.example.cinema.data.TopFilmsDto
import com.example.cinema.data.dataBases.CollectionEntity
import com.example.cinema.data.dataBases.CollectionListFilms
import com.example.cinema.data.dataBases.Dao
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.data.listItems.ListEpisodes
import com.example.cinema.domain.GetPageUseCase
import com.example.cinema.presentation.RandomFilmsCategory
import com.example.cinema.presentation.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val useCase: GetPageUseCase, private val collectionDao: Dao, private val randomPage: RandomFilmsCategory): ViewModel() {

    private val randomCountriesOne = randomPage.randomCountriesOne()
    private val randomGenresOne = randomPage.randomGenresOne()
    private val randomCountriesTwo = randomPage.randomCountriesTwo()
    private val randomGenresTwo = randomPage.randomGenresTwo()

    private val _state = MutableStateFlow<State>(State.Success)
    val state = _state.asStateFlow()
    init {
        load()
    }
    private fun load(){
        viewModelScope.launch(Dispatchers.IO){
            kotlin.runCatching {
                _state.value = State.Loading
            }
            delay(4000)
            _state.value = State.Success
        }
    }
    suspend fun getPremiersFilms(): List<PremiersFilmsDto>{
        return useCase.executeFilmPremier(year = 2024, month = "OCTOBER")
    }
    suspend fun getPopularFilms(): List<PopularFilmsDto>{
        return useCase.executePopularFilm(page = 1, type = "TOP_POPULAR_MOVIES")
    }
    suspend fun getTopFilms(): List<TopFilmsDto> {
        return useCase.executeFilmTop(page = 1, type = "TOP_250_MOVIES")
    }
    suspend fun getSelectionFilms(): List<SelectionFilmsDto>{
        return useCase.executeSelectionsFilm(page = 1, genres = randomGenresOne, countries = randomCountriesOne , type = "ALL" )
    }
    suspend fun getSelectionsFilmTwo(): List<SelectionFilmsTwoDto>{
        return useCase.executeSelectionsFilmTwo(page = 2, genres = randomGenresTwo, countries = randomCountriesTwo , type = "ALL")
    }
    suspend fun getSeriesFilms(): List<SeriesFilmsDto>{
        return useCase.executeSeriesFilm(page = 1, type = "TV_SERIES", order = "RATING" )
    }
    suspend fun getEpisodes(id: Int): List<ListEpisodes>{
        return useCase.executeEpisodes(id)
    }
    suspend fun apiInfo(idBundle: Int): InfoDto {
        return useCase.executeInfoFilm(idBundle)
    }
    suspend fun staff(filmId: Int): List<InfoStaffDto>{
        return useCase.executeInfoStaff(filmId)
    }
    suspend fun staffJobFilm(filmId: Int): List<StaffJobFilmDto>{
        return useCase.executeStaffJobFilm(filmId)
    }
    suspend fun gallery(id: Int): List<FilmGalleryDto>{
        return useCase.executeFilmGallery(id, page = 1, type = "STILL" )
    }
    suspend fun similarFilm(id: Int): List<SimilarFilmDto>{
        return useCase.executeSimilarFilm(id)
    }
    suspend fun actorFilmBestId(idBundle: Int): InfoActorDto {
        return useCase.executeActorInfo(idBundle)
    }
    suspend fun actorFilmBest(idBundle: Int): InfoActorDto {
        return useCase.executeFilterFilmographyInfo(idBundle, professionKey = "ACTOR")
    }

    suspend fun produsserFilmographi(idBundle: Int): InfoActorDto {
        return useCase.executeFilterFilmographyInfo(idBundle, professionKey = "PRODUCER")
    }

    suspend fun directorFilmographi(idBundle: Int): InfoActorDto {
        return useCase.executeFilterFilmographyInfo(idBundle, professionKey = "DIRECTOR")
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