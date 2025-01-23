package com.example.cinema.data.dataBases

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cinema.data.listItems.ListGenres

@Entity(tableName = "film_entity")
data class FilmEntity(
    @PrimaryKey
    @ColumnInfo(name = "film_id")
    val id: Int,
    @ColumnInfo(name = "poster")
    val posterUrl: String?,
    @ColumnInfo(name = "name")
    val nameRu: String?,
    )