package com.example.cinema.data.listItems

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListCountry @Inject constructor(
    @Json(name = "country")
    val country : String?,
    @Json(name = "id")
    val id : Int?
)