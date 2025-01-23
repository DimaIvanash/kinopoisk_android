package com.example.cinema.data.dataBases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow



@Dao
interface Dao {
    @Transaction
    @Query("SELECT * FROM collection_entity" )
      fun getAll(): Flow<List<CollectionWithFilms>>

    @Query("SELECT * FROM film_entity" )
    fun getFilm(): Flow<List<FilmEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE,)
    suspend fun insert(table: FilmEntity) // вставлять

    @Insert(onConflict = OnConflictStrategy.REPLACE,)
    suspend fun insertCollection(table: CollectionEntity) // вставлять

    @Insert(onConflict = OnConflictStrategy.REPLACE,)
    suspend fun insertFilmForCollection(table: CollectionListFilms)

    @Delete
    suspend fun deleteFilm(table: FilmEntity) // удаление данных из БД
    @Delete
    suspend fun deleteFilmForCollection(table: CollectionListFilms)
    @Delete
    suspend fun deleteCollection(table: CollectionEntity)



}