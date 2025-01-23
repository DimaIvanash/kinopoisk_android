package com.example.cinema.data.dataBases

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class CollectionWithFilms(
    @Embedded
    val collection: CollectionEntity,
    @Relation(
        parentColumn = "collection_id",
        entityColumn = "film_id",
        associateBy = Junction(
            CollectionListFilms::class,
            parentColumn = "collection_id",
            entityColumn = "film_id"
        )
    )
    var films: List<FilmEntity>?,
)
