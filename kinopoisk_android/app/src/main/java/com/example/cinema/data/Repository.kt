package com.example.cinema.data

import com.example.cinema.data.listItems.ListEpisodes
import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.data.listItems.ListItemsSearchSettingsFilms
import com.example.cinema.di.RepositoryModule
import kotlinx.coroutines.delay
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor(){
    private fun getPageApi(): PageApi {
        return RepositoryModule.provideRetrofit()
    }

    suspend fun getListPremier(year: Int, month: String): List<PremiersFilmsDto>{
        delay(500)
        return getPageApi().getPremieresApi(year, month).items
    }
    suspend fun getListTopFilm(page: Int, type: String): List<TopFilmsDto> {
        delay(500)
        return getPageApi().getTopFilmApi(page , type).items
    }
    suspend fun getListPopularFilm(page: Int, type: String): List<PopularFilmsDto>{
        delay(500)
        return getPageApi().getPopularFilmApi(page , type).items
    }
    suspend fun getListSeriesFilm(page: Int, type: String, order: String): List<SeriesFilmsDto>{
        delay(500)
        return getPageApi().getSeriesFilmApi(page, type, order).items
    }
    suspend fun getListEpisodes(id: Int): List<ListEpisodes>{
        return getPageApi().getEpisodesApi(id).items
    }

    suspend fun getListSelectionFilm(page: Int, genres: Int, countries: Int, type: String): List<SelectionFilmsDto>{
        delay(500)
        return getPageApi().getSelectionsFilmApi(page, genres, countries, type).items
    }
    suspend fun getListSelectionFilmTwo(page: Int,genres: Int, countries: Int, type: String): List<SelectionFilmsTwoDto>{
        delay(500)
        return getPageApi().getSelectionsTwoFilmApi(page, genres, countries, type).items
    }
    suspend fun getInfoFilm(id: Int): InfoDto{
        delay(500)
        return getPageApi().getInfoFilmApi(id)
    }
    suspend fun getInfoStaff(filmId: Int): List<InfoStaffDto>{
        delay(500)
        return getPageApi().getInfoStaffApi(filmId)
    }
    suspend fun getStaffJobFilm(filmId: Int): List<StaffJobFilmDto>{
        delay(500)
        return getPageApi().getStaffJobFilmApi(filmId)
    }
    suspend fun getFilmGallery(id: Int, page: Int, type: String): List<FilmGalleryDto>{
        delay(500)
        return getPageApi().getFilmGallery(id, page, type).items
    }

    suspend fun getSimilarFilm(id: Int): List<SimilarFilmDto>{
        delay(500)
        return getPageApi().getSimilarFilmApi(id).items
    }

    suspend fun getActorInfo(id: Int): InfoActorDto{
        delay(500)
        return getPageApi().getActorInfoApi(id)
    }
    suspend fun getFilterFilmographyInfo(id: Int, professionKey: String): InfoActorDto{
        delay(500)
        return getPageApi().getFilterFilmographyInfoApi(id, professionKey)
    }

    suspend fun getSearchByKeyword(keyword: String, page: Int): SearchKeywordDto{
        delay(500)
        return getPageApi().getSearchByKeywordApi(keyword, page)
    }
    suspend fun getSearchGenres(): ListInfoGenres{
        delay(500)
        return getPageApi().getSearchGenresApi()
    }
    suspend fun getSearchSettingsFilms(
        type: String?, countries: Int?, genres: Int?,
        yearFrom: Int?, yearTo: Int?, ratingFrom: Int?, ratingTo: Int?, order: String?
    ): ListItemsSearchSettingsFilms {
        delay(500)
        return getPageApi().getSearchSettingsFilmsApi(
            type, countries, genres, yearFrom, yearTo, ratingFrom, ratingTo, order
        )
    }





}