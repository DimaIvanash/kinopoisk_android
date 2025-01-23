package com.example.cinema.data.listItems

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListGenres @Inject constructor(
    @Json(name = "genre")
    val genre : String,
    @Json(name = "id")
    val id : Int?
)
