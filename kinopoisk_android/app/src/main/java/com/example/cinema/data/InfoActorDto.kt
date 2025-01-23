package com.example.cinema.data

import com.example.cinema.data.listItems.ListFilmsActor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class InfoActorDto (
    @Json(name = "personId")
    var personId: Int?,
    @Json(name = "nameRu")
    var nameRu: String?,
    @Json(name = "description")
    var description: String?,
    @Json(name = "posterUrl")
    var posterUrl: String?,
    @Json(name = "profession")
    var profession: String?,
    @Json(name = "films")
    var films: List<ListFilmsActor>?

)