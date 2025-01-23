package com.example.cinema.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cinema.data.SearchKeywordDto
import com.example.cinema.data.dataBases.Dao
import com.example.cinema.data.dataBases.FilmEntity
import com.example.cinema.data.dataBases.WatchedEntity
import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.data.listItems.ListItemsSearchSettingsFilms
import com.example.cinema.domain.GetPageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: GetPageUseCase, private val collectionDao: Dao): ViewModel(){
    suspend fun keyword(keyword: String, page: Int): SearchKeywordDto {

        return useCase.executeSearchByKeyword(keyword, page = 1)
    }
    suspend fun getSearchSettingsFilms(
        type: String?, countries: Int, genres: Int, yearFrom: Int?, yearTo: Int?, ratingFrom: Int?, ratingTo: Int?, order: String?
    ): ListItemsSearchSettingsFilms {
        return useCase.executeSearchSettingsFilms(
            type, countries, genres, yearFrom, yearTo, ratingFrom, ratingTo, order
        )
    }
    suspend fun getSearchIdGenreOrCountry(): ListInfoGenres {
        return useCase.executeSearchGenres()
    }
    suspend fun getGenres(newText: String): ListInfoGenres {
        return useCase.executeSearchGenres()
    }
    val allCollection = this.collectionDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000L),
            initialValue = emptyList()  //пустой список
        )
}