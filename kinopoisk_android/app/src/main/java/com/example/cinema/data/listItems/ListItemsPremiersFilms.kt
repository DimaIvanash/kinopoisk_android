package com.example.cinema.data.listItems

import com.example.cinema.data.PremiersFilmsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsPremiersFilms @Inject constructor(
    @Json(name = "items")
    val items: List<PremiersFilmsDto>
)
