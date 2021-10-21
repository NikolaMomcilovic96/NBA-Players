package com.raywenderlich.nbaplayers.ui.main

data class Player(
    val title: String,
    val desc: String,
    val image: Int,
    val height: String,
    val weight: String,
    val club: String
)

data class User(
    val username: String = "nikola",
    val password: String = "Momcilovic96"
)