package com.example.cinema.presentation

sealed class State {
    object Loading : State()
    object Success : State()
}


