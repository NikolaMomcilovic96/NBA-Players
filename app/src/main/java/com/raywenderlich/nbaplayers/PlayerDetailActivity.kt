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

        val playerTitle = intent.getStringExtra(Constants.Title.toString())
        val playerDesc = intent.getStringExtra(Constants.Desc.toString())
        val playerImage = intent.getIntExtra(Constants.Image.toString(), 0)
        val playerClub = intent.getStringExtra(Constants.Club.toString())
        val playerHeight = intent.getStringExtra(Constants.Height.toString())
        val playerWeight = intent.getStringExtra(Constants.Weight.toString())

        binding.playerTitleTextView.text = playerTitle.toString()
        binding.playerDescTextView.text = playerDesc.toString()
        binding.playerImageView.setImageResource(playerImage)
        binding.playerClubTextView.text = playerClub.toString()
        binding.playerHeightTextView.text = playerHeight.toString()
        binding.playerWeightTextView.text = playerWeight.toString()
    }
}