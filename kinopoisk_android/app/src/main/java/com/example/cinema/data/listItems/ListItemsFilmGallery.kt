package com.example.cinema.data.listItems

import com.example.cinema.data.FilmGalleryDto
import com.example.cinema.data.PopularFilmsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsFilmGallery @Inject constructor(
    @Json(name = "items")
    val items: List<FilmGalleryDto>
)