package com.raywenderlich.nbaplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.WindowCompat

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: PlayerDetailActivityBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        binding = PlayerDetailActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        showPlayerDetails()
    }

    private fun showPlayerDetails(){
        val playerName = intent.getStringExtra("title")
        val playerDesc = intent.getStringExtra("desc")
        val playerImage = intent.getIntExtra("image", 0)
        val playerClub = intent.getStringExtra("club")
        val playerHeight = intent.getStringExtra("height")
        val playerWeight = intent.getStringExtra("weight")
        findViewById<TextView>(R.id.playerTitleTextView).apply {
            text = playerName.toString()
        }
        findViewById<TextView>(R.id.playerDescTextView).apply {
            text = playerDesc.toString()
        }
        findViewById<ImageView>(R.id.playerImageView).apply {
            setImageResource(playerImage)
        }
        findViewById<TextView>(R.id.playerClubTextView).apply {
            text = playerClub.toString()
        }
        findViewById<TextView>(R.id.playerHeightTextView).apply {
            text = playerHeight.toString()
        }
        findViewById<TextView>(R.id.playerWeightTextView).apply {
            text = playerWeight.toString()
        }
    }
}