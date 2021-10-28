package com.raywenderlich.nbaplayers.ui.main

data class Player(
    val title: String,
    val desc: String,
    val image: Int,
    val height: String,
    val weight: String,
    val club: String,
)

data class User(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
    val birthday: String,
    val gender: String
)

val users: ArrayList<User> = arrayListOf()