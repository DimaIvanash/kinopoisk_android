package com.example.cinema.data.listItems

import com.example.cinema.data.TopFilmsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsTopFilms @Inject constructor(
    @Json(name = "items")
    val items: List<TopFilmsDto>
)