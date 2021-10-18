package com.raywenderlich.nbaplayers.ui.main

import android.content.Context
import android.content.SharedPreferences

data class Player(
    val title: String,
    val desc: String,
    val image: Int,
    val height: String,
    val weight: String,
    val club: String
)

object AppPreferences {
    private const val NAME = "TEST"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var sharedPreferences: SharedPreferences

    private val IS_LOGGED = Pair("is_logged", false)
    private val USERNAME = Pair("username", "")
    private val PASSWORD = Pair("password", "")
    private val FIRSTNAME = Pair("firstName", "")

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(NAME, MODE)
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var isLogged: Boolean
        get() = sharedPreferences.getBoolean(IS_LOGGED.first, IS_LOGGED.second)
        set(value) = sharedPreferences.edit {
            it.putBoolean(IS_LOGGED.first, value)
        }

    var username: String
        get() = sharedPreferences.getString(USERNAME.first, USERNAME.second) ?: ""
        set(value) = sharedPreferences.edit {
            it.putString(USERNAME.first, value)
        }

    var password: String
        get() = sharedPreferences.getString(PASSWORD.first, PASSWORD.second) ?: ""
        set(value) = sharedPreferences.edit {
            it.putString(PASSWORD.first, value)
        }

    var firstName: String
        get() = sharedPreferences.getString(FIRSTNAME.first, FIRSTNAME.second) ?: ""
        set(value) = sharedPreferences.edit {
            it.putString(FIRSTNAME.first, value)
        }
}