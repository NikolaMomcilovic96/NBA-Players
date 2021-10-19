package com.raywenderlich.nbaplayers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raywenderlich.nbaplayers.databinding.ActivityLoginBinding
import com.raywenderlich.nbaplayers.ui.main.User
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences =
            getSharedPreferences(R.string.sharedPref.toString(), Context.MODE_PRIVATE)

        isLoggedIn()

        binding.loginButton.setOnClickListener {
            inputCheck()
        }
    }

    private fun inputCheck() {
        val user = User()

        val username = binding.userNameEditText.text?.toString()
        val password = binding.passwordEditText.text?.toString()
        val newUsername = username?.replace("\\s".toRegex(), "")
        val newPassword = password?.replace("\\s".toRegex(), "")

        if (newUsername
                .equals("") || newPassword.equals("")
        ) {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
        } else if (newUsername?.length!! < 4) {
            Toast.makeText(this, R.string.short_username, Toast.LENGTH_SHORT).show()
        } else if (!passwordFormatCheck(newPassword.toString())) {
            Toast.makeText(this, R.string.password_format, Toast.LENGTH_SHORT).show()
        } else {
            if (newUsername.toString() == user.username && newPassword.toString() == user.password) {
                newActivity(user)
            } else if (newUsername.toString() == user.username && newPassword.toString() != user.password) {
                binding.passwordEditText.text?.clear()
                Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show()
            } else if (newUsername.toString() != user.username && newPassword.toString() == user.password) {
                binding.userNameEditText.text?.clear()
                Toast.makeText(this, R.string.wrong_username, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun passwordFormatCheck(pass: String): Boolean {
        val regex: Pattern =
            Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}${'$'}")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun isLoggedIn() {
        val isLogged = sharedPreferences.getBoolean(R.string.IS_LOGGED.toString(), false)

        if (isLogged) {
            val username = sharedPreferences.getString(R.string.USERNAME.toString(), "").toString()
            val password = sharedPreferences.getString(R.string.PASSWORD.toString(), "").toString()
            val user = User(username, password)

            newActivity(user)
        }
    }

    private fun newActivity(user: User) {
        val editor = sharedPreferences.edit()
        editor.putString(R.string.USERNAME.toString(), user.username)
        editor.putString(R.string.PASSWORD.toString(), user.password)
        editor.putBoolean(R.string.IS_LOGGED.toString(), true)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}