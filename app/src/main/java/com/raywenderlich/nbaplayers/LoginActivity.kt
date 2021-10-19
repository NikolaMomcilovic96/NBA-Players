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

        sharedPreferences = getSharedPreferences(R.string.sharedPref.toString(), Context.MODE_PRIVATE)

        isLoggedIn()

        binding.loginButton.setOnClickListener {
            inputCheck()
        }
    }

    private fun inputCheck() {
        val user = User()

        if (binding.userNameEditText.text?.toString()
                .equals("") || binding.passwordEditText.text?.toString().equals("")
        ) {
            Toast.makeText(this, R.string.empty_fields, Toast.LENGTH_SHORT).show()
        } else if (binding.userNameEditText.text?.length!! < 4) {
            Toast.makeText(this, R.string.short_username, Toast.LENGTH_SHORT).show()
        } else if (!passwordCheck(binding.passwordEditText.text.toString())) {
            Toast.makeText(this, R.string.password_format, Toast.LENGTH_SHORT).show()
        } else {
            if (binding.userNameEditText.text.toString() == user.username && binding.passwordEditText.text.toString() == user.password) {
                newActivity(user)
            } else if (binding.userNameEditText.text.toString() == user.username && binding.passwordEditText.text.toString() != user.password) {
                binding.passwordEditText.text?.clear()
                Toast.makeText(this, R.string.wrong_password, Toast.LENGTH_SHORT).show()
            } else if (binding.userNameEditText.text.toString() != user.username && binding.passwordEditText.text.toString() == user.password) {
                binding.userNameEditText.text?.clear()
                Toast.makeText(this, R.string.wrong_username, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, R.string.login_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun passwordCheck(pass: String): Boolean {
        val regex: Pattern =
            Pattern.compile("""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}${'$'}""")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun newActivity(user: User) {
        val username = user.username
        val password = user.password
        val firstName = user.firstName
        val isLogged = true

        val editor = sharedPreferences.edit()
        editor.putString(R.string.USERNAME.toString(), username)
        editor.putString(R.string.PASSWORD.toString(), password)
        editor.putString(R.string.FIRSTNAME.toString(), firstName)
        editor.putBoolean(R.string.IS_LOGGED.toString(), isLogged)
        editor.apply()

        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun isLoggedIn() {
        sharedPreferences = getSharedPreferences(R.string.sharedPref.toString(), Context.MODE_PRIVATE)
        val isLogged = sharedPreferences.getBoolean(R.string.IS_LOGGED.toString(), false)

        if (isLogged) {
            val username = sharedPreferences.getString(R.string.USERNAME.toString(), "").toString()
            val password = sharedPreferences.getString(R.string.PASSWORD.toString(), "").toString()
            val firstName = sharedPreferences.getString(R.string.FIRSTNAME.toString(), "").toString()
            val user = User(username, password, firstName)

            newActivity(user)
        }
    }
}