package com.example.cinema.data.dataBases

data class WatchedEntity(
    val id: Int?,
    val name: String?,
    val poster: String?,
    var visibility: Boolean? = false
)
