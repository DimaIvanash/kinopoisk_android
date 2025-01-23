package com.example.cinema.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SimilarFilmDto (
    @Json(name = "filmId")
    val filmId: Int?,
    @Json(name = "nameRu")
    val nameRu: String?,
    @Json(name = "nameEn")
    val nameEn: String?,
    @Json(name = "nameOriginal")
    val nameOriginal: String?,
    @Json(name = "posterUrl")
    val posterUrl: String?,
    @Json(name = "relationType")
    val relationType: String
)