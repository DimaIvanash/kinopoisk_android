package com.example.cinema.entity

import com.example.cinema.data.listItems.ListGenres
import com.squareup.moshi.Json

interface MoveParameters {
    val id : Int
    val nameRu : String?
    val posterUrl : String?
    val genres : List<ListGenres>
}