package com.raywenderlich.nbaplayers.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    }
}