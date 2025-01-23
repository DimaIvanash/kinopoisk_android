package com.example.cinema.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FilmGalleryDto (
    @Json(name = "imageUrl")
    var imageUrl: String?
)
