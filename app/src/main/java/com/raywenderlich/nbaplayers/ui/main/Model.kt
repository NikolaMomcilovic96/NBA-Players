package com.raywenderlich.nbaplayers.ui.main

data class Model(
    val title: String,
    val desc: String,
    val image: Int,
    val height: String,
    val weight: String,
    val club: String
)

data class User(
    val username: String = "Nikola",
    val password: String = "Momcilovic96"
)