package com.example.cinema.presentation


import javax.inject.Inject
import kotlin.random.Random
import kotlin.random.nextInt

class RandomFilmsCategory @Inject constructor() {
    fun randomGenresOne(): Int{
        return Random.nextInt(1..5)
    }
    fun randomCountriesOne(): Int {
        return Random.nextInt(1..5)
    }
    fun randomGenresTwo(): Int{
        return Random.nextInt(1..5)
    }
    fun randomCountriesTwo(): Int {
        return Random.nextInt(1..5)
    }

}