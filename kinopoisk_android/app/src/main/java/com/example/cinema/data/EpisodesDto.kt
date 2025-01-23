package com.example.cinema.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class EpisodesDto @Inject constructor(
    @Json(name = "seasonNumber")
    val seasonNumber: Int?,
    @Json(name = "episodeNumber")
    val episodeNumber: Int?,
    @Json(name = "releaseDate")
    val releaseDate: String?,
    @Json(name = "nameRu")
    val nameRu: String?,
)