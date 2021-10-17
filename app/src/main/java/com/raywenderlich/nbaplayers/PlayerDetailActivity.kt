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

    private fun showPlayerDetails(){
        val playerTitle = intent.getStringExtra("title")
        val playerDesc = intent.getStringExtra("desc")
        val playerImage = intent.getIntExtra("image", 0)
        val playerClub = intent.getStringExtra("club")
        val playerHeight = intent.getStringExtra("height")
        val playerWeight = intent.getStringExtra("weight")

        binding.playerTitleTextView.apply {
            text = playerTitle.toString()
        }
        binding.playerDescTextView.apply {
            text=playerDesc.toString()
        }
        binding.playerImageView.apply {
            setImageResource(playerImage)
        }
        binding.playerClubTextView.apply {
            text = playerClub.toString()
        }
        binding.playerHeightTextView.apply {
            text = playerHeight.toString()
        }
        binding.playerWeightTextView.apply {
            text = playerWeight.toString()
        }
    }
}