package com.example.cinema.data.listItems

import com.example.cinema.data.SearchFilmsSettings
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListItemsSearchSettingsFilms @Inject constructor(
    @Json(name = "items")
    val items: List<SearchFilmsSettings>,
    @Json(name = "total")
    val total: Int?,
    @Json(name = "totalPages")
    val totalPages: Int?
)