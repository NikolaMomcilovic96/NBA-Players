package com.raywenderlich.nbaplayers

import android.content.ContentProviderOperation
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.MainActivityBinding
import com.raywenderlich.nbaplayers.ui.main.AppPreferences
import com.raywenderlich.nbaplayers.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        AppPreferences.init(this)

        Toast.makeText(this, "Hello ${AppPreferences.firstName}", Toast.LENGTH_LONG).show()
    }


}