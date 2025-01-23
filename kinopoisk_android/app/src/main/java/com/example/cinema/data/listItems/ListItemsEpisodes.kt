package com.example.cinema.data.listItems

import com.example.cinema.data.SeriesFilmsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject
@JsonClass(generateAdapter = true)
class ListItemsEpisodes @Inject constructor(
    @Json(name = "items")
    val items: List<ListEpisodes>,
    @Json(name = "total")
    val total: Int
)