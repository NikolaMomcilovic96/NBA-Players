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

        val playerTitle = intent.getStringExtra(Constants.title)
        val playerDesc = intent.getStringExtra(Constants.desc)
        val playerImage = intent.getIntExtra(Constants.image, 0)
        val playerClub = intent.getStringExtra(Constants.club)
        val playerHeight = intent.getStringExtra(Constants.height)
        val playerWeight = intent.getStringExtra(Constants.weight)

        binding.playerTitleTextView.text = playerTitle.toString()
        binding.playerDescTextView.text = playerDesc.toString()
        binding.playerImageView.setImageResource(playerImage)
        binding.playerClubTextView.text = playerClub.toString()
        binding.playerHeightTextView.text = playerHeight.toString()
        binding.playerWeightTextView.text = playerWeight.toString()
    }
}