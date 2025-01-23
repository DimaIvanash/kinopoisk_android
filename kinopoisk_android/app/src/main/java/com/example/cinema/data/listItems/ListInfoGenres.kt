package com.example.cinema.data.listItems

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListInfoGenres @Inject constructor(
    @Json(name = "genres")
    val items: List<ListGenres>,
    @Json(name = "countries")
    val countries: List<ListCountry>
) {

}