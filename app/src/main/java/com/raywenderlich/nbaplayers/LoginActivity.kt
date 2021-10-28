package com.raywenderlich.nbaplayers

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raywenderlich.nbaplayers.databinding.ActivityLoginBinding
import java.util.regex.Matcher
import java.util.regex.Pattern
import android.content.Intent
import com.raywenderlich.nbaplayers.ui.main.User

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences =
            getSharedPreferences(Constants.sharedPref, Context.MODE_PRIVATE)

        val isUsernameSaved = sharedPreferences.getString(Constants.USERNAME, "")

        if (isUsernameSaved.isNullOrEmpty()) {
            binding.loginButton.setOnClickListener {
                inputCheck()
            }
        } else {
            val username = sharedPreferences.getString(Constants.USERNAME, "").toString()
            val password = sharedPreferences.getString(Constants.PASSWORD, "").toString()

            newActivity(username, password)
        }

        binding.createAccoutButton.setOnClickListener {
            startActivity(Intent(this, RegistrationActivity::class.java))
        }
    }

    private fun inputCheck() {
        val user = User()

        val username = binding.userNameEditText.text?.toString()
        username?.trim()
        val password = binding.passwordEditText.text?.toString()
        password?.trim()

        when {
            username.isNullOrEmpty() || password.isNullOrEmpty() -> toastMessage(R.string.empty_fields)
            username.length < 4 -> toastMessage(R.string.short_username)
            !passwordFormatCheck(password) -> toastMessage(R.string.password_format)
            username == user.username && password == user.password -> newActivity(
                username, password)
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

    private fun newActivity(username: String, password: String) {
        sharedPreferences.edit().apply {
            putString(Constants.USERNAME, username)
            putString(Constants.PASSWORD, password)
        }.apply()

        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onBackPressed() {
        finish()
    }
}



