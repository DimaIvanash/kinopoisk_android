package com.example.cinema.data.listItems

import com.example.cinema.data.SelectionFilmsTwoDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsSelectionsFilmsTwo @Inject constructor(
    @Json(name = "items")
    val items: List<SelectionFilmsTwoDto>
)