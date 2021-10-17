package com.raywenderlich.nbaplayers.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.raywenderlich.nbaplayers.R
import com.raywenderlich.nbaplayers.ui.detail.ui.detail.PlayerDetailFragment

class PlayerDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PlayerDetailFragment.newInstance())
                .commitNow()
        }
    }
}