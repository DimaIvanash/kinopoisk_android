package com.example.cinema.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class StaffJobFilmDto (
    @Json(name = "staffId")
    var staffId: Int?,
    @Json(name = "nameRu")
    var nameRu: String?,
    @Json(name = "professionText")
    var professionText: String?,
    @Json(name = "posterUrl")
    var posterUrl: String?

)