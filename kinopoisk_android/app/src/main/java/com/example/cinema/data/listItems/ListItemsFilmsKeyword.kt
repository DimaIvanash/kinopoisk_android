package com.example.cinema.data.listItems

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ListItemsFilmsKeyword (
    @Json(name = "filmId")
     val filmId : Int?,
    @Json(name = "nameRu")
    val nameRu : String?,
    @Json(name = "posterUrl")
    val posterUrl : String?,
    @Json(name = "genres")
    val genres : List<ListGenres>?,
    @Json(name = "year")
    val year : String?,

)