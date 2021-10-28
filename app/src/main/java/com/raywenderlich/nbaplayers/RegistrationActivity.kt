package com.raywenderlich.nbaplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.ActivityRegistrationBinding

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val wikipediaLink = binding.privacyPolicyLink
        wikipediaLink.movementMethod = LinkMovementMethod.getInstance()
    }
}