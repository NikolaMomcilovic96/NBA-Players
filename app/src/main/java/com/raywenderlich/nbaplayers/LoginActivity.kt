package com.raywenderlich.nbaplayers

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raywenderlich.nbaplayers.databinding.ActivityLoginBinding
import com.raywenderlich.nbaplayers.ui.main.AppPreferences
import com.raywenderlich.nbaplayers.ui.main.Model
import com.raywenderlich.nbaplayers.ui.main.User
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        AppPreferences.init(this)
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
        val regex: Pattern = Pattern.compile("""^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{6,}$""")
        val text: Matcher = regex.matcher(pass)
        return text.matches()
    }

    private fun newActivity(user: User) {
        AppPreferences.username = user.username
        AppPreferences.password = user.password
        AppPreferences.firstName = user.firstName
        AppPreferences.isLogged = true

        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun isLoggedIn() {
        if (AppPreferences.isLogged) {
            val user =
                User(AppPreferences.username, AppPreferences.password, AppPreferences.firstName)
            newActivity(user)
        }
    }
}