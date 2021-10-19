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

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

        sharedPreferences = getSharedPreferences(R.string.sharedPref.toString(), Context.MODE_PRIVATE)
        val firstName = sharedPreferences.getString(R.string.FIRSTNAME.toString(),"")

        Toast.makeText(this, "Hello $firstName", Toast.LENGTH_LONG).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_item) {
            sharedPreferences = getSharedPreferences(R.string.sharedPref.toString(),Context.MODE_PRIVATE)
            val isLogged = false
            val editor = sharedPreferences.edit()
            editor.putBoolean(R.string.IS_LOGGED.toString(), isLogged)
            editor.apply()
            startActivity(Intent(this, LoginActivity::class.java))
            Toast.makeText(this, R.string.logout_message, Toast.LENGTH_LONG).show()
        }
        return true
    }
}