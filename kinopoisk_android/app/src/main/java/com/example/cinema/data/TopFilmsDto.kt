package com.example.cinema.data

import com.example.cinema.data.listItems.ListGenres
import com.example.cinema.entity.MoveParameters
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TopFilmsDto(
    @Json(name = "kinopoiskId")
    override val id : Int,
    @Json(name = "nameRu")
    override val nameRu : String,
    @Json(name = "posterUrl")
    override val posterUrl : String,
    @Json(name = "genres")
    override val genres : List<ListGenres>,
    ): MoveParameters
