package com.example.cinema.data

import com.example.cinema.data.listItems.ListInfoGenres
import com.example.cinema.data.listItems.ListItemsFilmsKeyword
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchKeywordDto (
    @Json(name = "keyword")
    val keyword : String?,
    @Json(name = "films")
    val films : List<ListItemsFilmsKeyword>,
    )