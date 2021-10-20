package com.raywenderlich.nbaplayers

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raywenderlich.nbaplayers.databinding.ActivityLoginBinding
import com.raywenderlich.nbaplayers.ui.main.User
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.content.Intent

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences =
            getSharedPreferences(R.string.sharedPref.toString(), Context.MODE_PRIVATE)

        val isUserLoggedIn = sharedPreferences.getBoolean(R.string.IS_USER_LOGGED_IN.toString(), false)

        if (isUserLoggedIn) {
            val username = sharedPreferences.getString(R.string.USERNAME.toString(), "").toString()
            val password = sharedPreferences.getString(R.string.PASSWORD.toString(), "").toString()
            val user = User(username, password)

            newActivity(user)
        }

        binding.loginButton.setOnClickListener {
            inputCheck()
        }
    }

    private fun inputCheck() {
        val user = User()

        val username = binding.userNameEditText.text?.toString()
        val password = binding.passwordEditText.text?.toString()

        when {
            username.isNullOrEmpty() || password.isNullOrEmpty() -> toastMessage(R.string.empty_fields)
            username.length < 4 -> toastMessage(R.string.short_username)
            !passwordFormatCheck(password.toString()) -> toastMessage(R.string.password_format)
            username.toString() == user.username && password.toString() == user.password -> newActivity(user)
            else -> toastMessage(R.string.login_error)
        }
    }

    private fun toastMessage(message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun passwordFormatCheck(pass: String): Boolean {
        val regex: Pattern =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}${'$'}")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun newActivity(user: User) {
        sharedPreferences.edit().apply {
            putString(R.string.USERNAME.toString(), user.username)
            putString(R.string.PASSWORD.toString(), user.password)
            putBoolean(R.string.IS_USER_LOGGED_IN.toString(), true)
        }.apply()

        startActivity(Intent(this, MainActivity::class.java))
    }
}



