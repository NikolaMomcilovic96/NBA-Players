package com.raywenderlich.nbaplayers.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.raywenderlich.nbaplayers.R
import com.raywenderlich.nbaplayers.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginButton.setOnClickListener {
            if (binding.userNametEditText.text.toString() == "Nikola" && binding.passwordEditText.text.toString() == "Momcilovic"){
                Toast.makeText(this, "Uspeh", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Greska", Toast.LENGTH_SHORT).show()
            }
        }
    }
}