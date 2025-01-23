package com.example.cinema.data

import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.data.listItems.ListItemsEpisodes
import com.example.cinema.data.listItems.ListItemsFilmGallery
import com.example.cinema.data.listItems.ListItemsPremiersFilms
import com.example.cinema.data.listItems.ListItemsPopularFilm
import com.example.cinema.data.listItems.ListItemsSearchSettingsFilms
import com.example.cinema.data.listItems.ListItemsSelectionsFilms
import com.example.cinema.data.listItems.ListItemsSelectionsFilmsTwo
import com.example.cinema.data.listItems.ListItemsSeriesFilms
import com.example.cinema.data.listItems.ListItemsSimilarFilms
import com.example.cinema.data.listItems.ListItemsTopFilms
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface PageApi {
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/premieres")
    suspend fun getPremieresApi(
        @Query("year") year: Int,
        @Query("month") month: String): ListItemsPremiersFilms
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/collections")
    suspend fun getTopFilmApi(
        @Query("page") page: Int,
        @Query("type") type: String): ListItemsTopFilms
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/collections")
    suspend fun getPopularFilmApi(
        @Query("page") page: Int,
        @Query("type") type: String): ListItemsPopularFilm
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films?countries=1&genres=2&order=RATING&ratingFrom=2&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSeriesFilmApi(
        @Query("page") page: Int,
        @Query("type") type: String,
        @Query("order") order: String): ListItemsSeriesFilms
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/{id}/seasons")
    suspend fun getEpisodesApi(
        @Path("id") id: Int): ListItemsEpisodes
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films?&order=RATING&ratingFrom=2&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSelectionsFilmApi(
        @Query("page") page: Int,
        @Query("genres") genres: Int,
        @Query("countries") countries: Int,
        @Query("type") type: String): ListItemsSelectionsFilms
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films?&order=RATING&ratingFrom=1&ratingTo=10&yearFrom=1000&yearTo=3000")
    suspend fun getSelectionsTwoFilmApi(
        @Query("page") page: Int,
        @Query("genres") genres: Int,
        @Query("countries") countries: Int,
        @Query("type") type: String): ListItemsSelectionsFilmsTwo
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/{id}")
    suspend fun getInfoFilmApi(
        @Path("id") id: Int): InfoDto
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v1/staff")
    suspend fun getInfoStaffApi(
        @Query("filmId") filmId: Int): List<InfoStaffDto>
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v1/staff")
    suspend fun getStaffJobFilmApi(
        @Query("filmId") filmId: Int): List<StaffJobFilmDto>
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/{id}/images")
    suspend fun getFilmGallery(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("type") type: String): ListItemsFilmGallery

    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/{id}/similars")
    suspend fun getSimilarFilmApi(
        @Path("id") id: Int):ListItemsSimilarFilms

    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v1/staff/{id}")
    suspend fun getActorInfoApi(
        @Path("id") id: Int): InfoActorDto
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v1/staff/{id}")
    suspend fun getFilterFilmographyInfoApi(
        @Path("id") id: Int,
        @Query("professionKey") professionKey: String): InfoActorDto
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.1/films/search-by-keyword")
    suspend fun getSearchByKeywordApi(
        @Query("keyword") keyword: String,
        @Query("page") page: Int): SearchKeywordDto
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films/filters")
    suspend fun getSearchGenresApi(
    ): ListInfoGenres
    @Headers("X-API-KEY: $apiKey")
    @GET("/api/v2.2/films")
    suspend fun getSearchSettingsFilmsApi(
        @Query("type") type: String?,
        @Query("countries") countries: Int?,
        @Query("genres") genres: Int?,
        @Query("yearFrom") yearFrom: Int?,
        @Query("yearTo") yearTo: Int?,
        @Query("ratingFrom") ratingFrom: Int?,
        @Query("ratingTo") ratingTo: Int?,
        @Query("order") order: String?,): ListItemsSearchSettingsFilms
    companion object{
        private const val apiKey =
            "ef854601-531e-4182-9c04-03f4c3f232a1"
//           "5bc2aa88-9723-427e-a405-47d20ecca2d5"
//           "a3ebc6f8-64fa-4f97-9b0c-0da71315a41a"
//            "50afa2a9-60f5-42a5-ad61-a7b10cbadf74"
    }
}