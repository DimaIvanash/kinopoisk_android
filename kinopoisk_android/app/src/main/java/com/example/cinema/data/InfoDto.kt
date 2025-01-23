package com.example.cinema.data

import com.example.cinema.data.listItems.ListCountry
import com.example.cinema.data.listItems.ListGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoDto(
    @Json(name = "kinopoiskId")
    var id: Int,
    @Json(name = "description")
    val description: String?,
    @Json(name = "posterUrl")
    val posterUrl: String?,
    @Json(name = "webUrl")
    val webUrl: String?,
    @Json(name = "ratingKinopoisk")
    val ratingKinopoisk: Double?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "year")
    val year: String?,
    @Json(name = "genres")
    val genres: List<ListGenres>,
    @Json(name = "countries")
    val countries: List<ListCountry>,
    @Json(name = "filmLength")
    val filmLength: Int?,
    @Json(name = "ratingAgeLimits")
    val ratingAgeLimits: String?,
    )
