package com.raywenderlich.nbaplayers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.MainActivityBinding
import com.raywenderlich.nbaplayers.ui.main.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        sharedPreferences =
            getSharedPreferences(Constants.sharedPref, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.USERNAME, "")

        Toast.makeText(this, "${Constants.hello_msg} $username", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_item) {
            val editor = sharedPreferences.edit()
            editor.putString(Constants.USERNAME, "")
            editor.apply()
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, R.string.logout_message, Toast.LENGTH_LONG).show()
        }
        return true
    }
}