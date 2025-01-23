package com.example.cinema.data.listItems

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListFilmsActor (
    @Json(name = "filmId")
    var filmId: Int?,
    @Json(name = "nameRu")
    var nameRu: String?,
    @Json(name = "rating")
    var rating: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "professionKey")
    var professionKey: String?

)