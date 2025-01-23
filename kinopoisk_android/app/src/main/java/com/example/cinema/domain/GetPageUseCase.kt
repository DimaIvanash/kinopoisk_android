package com.example.cinema.domain

import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.data.InfoActorDto
import com.example.cinema.data.InfoDto
import com.example.cinema.data.InfoStaffDto
import com.example.cinema.data.Repository
import com.example.cinema.data.PremiersFilmsDto
import com.example.cinema.data.PopularFilmsDto
import com.example.cinema.data.SearchKeywordDto
import com.example.cinema.data.SelectionFilmsDto
import com.example.cinema.data.SelectionFilmsTwoDto
import com.example.cinema.data.SeriesFilmsDto
import com.example.cinema.data.SimilarFilmDto
import com.example.cinema.data.TopFilmsDto
import com.example.cinema.data.StaffJobFilmDto
import com.example.cinema.data.listItems.ListEpisodes
import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.data.listItems.ListItemsSearchSettingsFilms
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetPageUseCase @Inject constructor() {
    private val repository = Repository()
    suspend fun executeFilmPremier(year: Int, month: String): List<PremiersFilmsDto>{
        return repository.getListPremier(year, month)
    }
    suspend fun executeFilmTop(page: Int, type: String): List<TopFilmsDto>{
        return repository.getListTopFilm(page, type)
    }
    suspend fun executePopularFilm(page: Int, type: String): List<PopularFilmsDto>{
        return repository.getListPopularFilm(page, type)
    }
    suspend fun executeSeriesFilm(page: Int, type: String, order: String ): List<SeriesFilmsDto>{
        return repository.getListSeriesFilm(page, type, order)
    }
    suspend fun executeEpisodes(id: Int): List<ListEpisodes>{
        return repository.getListEpisodes(id)
    }
    suspend fun executeSelectionsFilm(page: Int, genres: Int, countries: Int, type: String): List<SelectionFilmsDto> {
        return repository.getListSelectionFilm(page,genres,countries, type)
    }
    suspend fun executeSelectionsFilmTwo(page: Int, genres: Int, countries: Int, type: String): List<SelectionFilmsTwoDto> {
        return repository.getListSelectionFilmTwo(page,genres,countries, type)
    }
    suspend fun executeInfoFilm(id: Int): InfoDto {
        return repository.getInfoFilm(id)
    }
    suspend fun executeInfoStaff(filmId: Int): List<InfoStaffDto>{
        return repository.getInfoStaff(filmId)
    }
    suspend fun executeStaffJobFilm(filmId: Int): List<StaffJobFilmDto>{
        return repository.getStaffJobFilm(filmId)
    }
    suspend fun executeFilmGallery(id: Int, page: Int, type: String): List<FilmGalleryDto>{
        return repository.getFilmGallery(id, page, type)
    }
    suspend fun executeSimilarFilm(id: Int): List<SimilarFilmDto>{
        return repository.getSimilarFilm(id)
    }
    suspend fun executeActorInfo(id: Int): InfoActorDto{
        return repository.getActorInfo(id)
    }
    suspend fun executeFilterFilmographyInfo(id: Int, professionKey: String): InfoActorDto{
        return repository.getFilterFilmographyInfo(id, professionKey)
    }
    suspend fun executeSearchByKeyword(keyword: String, page: Int): SearchKeywordDto {
        return repository.getSearchByKeyword(keyword, page)
    }
    suspend fun executeSearchGenres(): ListInfoGenres {
        return repository.getSearchGenres()
    }
    suspend fun executeSearchSettingsFilms(
        type: String?, countries: Int?, genres: Int?, yearFrom: Int?,
        yearTo: Int?, ratingFrom: Int?, ratingTo: Int?, order: String?
    ): ListItemsSearchSettingsFilms {
        return repository.getSearchSettingsFilms(
            type, countries, genres, yearFrom, yearTo, ratingFrom, ratingTo, order
        )
    }





}