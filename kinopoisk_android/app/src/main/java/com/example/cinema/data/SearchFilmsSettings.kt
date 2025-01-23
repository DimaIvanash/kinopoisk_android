package com.example.cinema.data

import com.example.cinema.data.listItems.ListCountry
import com.example.cinema.data.listItems.ListGenres
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchFilmsSettings (
    @Json(name = "kinopoiskId")
    val id: Int?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "year")
    val year: Int?,
    @Json(name = "posterUrl")
    val posterUrl : String?
)

