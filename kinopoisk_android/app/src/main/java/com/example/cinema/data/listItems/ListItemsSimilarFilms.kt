package com.example.cinema.data.listItems

import com.example.cinema.data.SimilarFilmDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsSimilarFilms @Inject constructor(
    @Json(name = "items")
    val items: List<SimilarFilmDto>,
    @Json(name = "total")
    val total: Int?
)