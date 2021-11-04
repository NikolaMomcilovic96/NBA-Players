package com.raywenderlich.nbaplayers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import com.raywenderlich.nbaplayers.databinding.ActivityPlayerDetailBinding

class PlayerDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        binding = ActivityPlayerDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val playerTitle = intent.getStringExtra(Constants.TITLE_TEXT)
        val playerDesc = intent.getStringExtra(Constants.DESCRIPTION_TEXT)
        val playerImage = intent.getIntExtra(Constants.IMAGE_TEXT, 0)
        val playerClub = intent.getStringExtra(Constants.CLUB_TEXT)
        val playerHeight = intent.getStringExtra(Constants.HEIGHT_TEXT)
        val playerWeight = intent.getStringExtra(Constants.WEIGHT_TEXT)

        binding.playerTitleTextView.text = playerTitle.toString()
        binding.playerDescTextView.text = playerDesc.toString()
        binding.playerImageView.setImageResource(playerImage)
        binding.playerClubTextView.text = playerClub.toString()
        binding.playerHeightTextView.text = playerHeight.toString()
        binding.playerWeightTextView.text = playerWeight.toString()
    }
}