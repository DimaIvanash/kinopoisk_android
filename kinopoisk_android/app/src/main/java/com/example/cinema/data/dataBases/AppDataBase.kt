package com.example.cinema.data.dataBases

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FilmEntity::class, CollectionEntity::class, CollectionListFilms::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun dao(): Dao
}

