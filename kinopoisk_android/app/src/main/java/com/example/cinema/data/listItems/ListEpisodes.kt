package com.example.cinema.data.listItems

import com.example.cinema.data.EpisodesDto
import com.example.cinema.data.SeriesFilmsDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import javax.inject.Inject

@JsonClass(generateAdapter = true)
class ListEpisodes @Inject constructor(
    @Json(name = "number")
    var number: Int,
    @Json(name = "episodes")
    val episodes: List<EpisodesDto>
)