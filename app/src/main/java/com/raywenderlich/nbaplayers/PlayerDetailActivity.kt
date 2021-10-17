package com.raywenderlich.nbaplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.ActivityPlayerDetailBinding

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_detail)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        showPlayerDetails()
    }

    private fun showPlayerDetails() {
        val playerTitle = intent.getStringExtra("title")
        val playerDesc = intent.getStringExtra("desc")
        val playerImage = intent.getIntExtra("image", 0)
        val playerClub = intent.getStringExtra("club")
        val playerHeight = intent.getStringExtra("height")
        val playerWeight = intent.getStringExtra("weight")

        binding.playerTitleTextView.text = playerTitle.toString()
        binding.playerDescTextView.text = playerDesc.toString()
        binding.playerImageView.setImageResource(playerImage)
        binding.playerClubTextView.text = playerClub.toString()
        binding.playerHeightTextView.text = playerHeight.toString()
        binding.playerWeightTextView.text = playerWeight.toString()
    }
}