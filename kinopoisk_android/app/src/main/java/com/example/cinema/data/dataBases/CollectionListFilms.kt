package com.example.cinema.data.dataBases

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(
    tableName = "collection_film",
    primaryKeys = ["collection_id", "film_id"]
)
data class CollectionListFilms(
    @ColumnInfo(name = "collection_id")
    val collectionId: Int,
    @ColumnInfo(name = "film_id")
    val filmId: Int,
    )