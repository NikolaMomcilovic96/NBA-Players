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
    val firstName: String = "Nikola",
    val lastName: String = "Momcilovic",
    val username: String = "nikola",
    val email: String = "nikolamomca96@gmail.com",
    val password: String = "Momcilovic.96",
    val birthday: String = "05-12-1996",
    val gender: String = "Male"
)