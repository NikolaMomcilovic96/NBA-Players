package com.raywenderlich.nbaplayers.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.nbaplayers.R
import com.raywenderlich.nbaplayers.databinding.ListSelectionViewHolderBinding

class ListSelectionRecyclerViewAdapter : RecyclerView.Adapter<ListSelectionViewHolder>() {

    private val players = arrayOf(
        Model(
            "Luka Doncic",
            "Luka Dončić is a Slovenian professional basketball player for the Dallas Mavericks of the National Basketball Association. He also represents the Slovenian national team.",
            R.drawable.doncic
        ),
        Model(
            "Ognjen Jaramaz",
            "Ognjen Jaramaz is a Serbian professional basketball player for Bayern Munich of the Basketball Bundesliga and the EuroLeague. Standing at 1.93 m, he plays the shooting guard position.",
            R.drawable.jaramaz
        ),
        Model(
            "Nikola Jokic",
            "Nikola Jokić is a Serbian professional basketball player for the Denver Nuggets of the National Basketball Association who plays the center position. The three-time NBA All-Star has been named to the All-NBA Team on three occasions, and has won the NBA Most Valuable Player Award for the 2020–21 NBA season.",
            R.drawable.jokic
        ),
        Model(
            "Nemanja Bjelica",
            "Nemanja Bjelica is a Serbian professional basketball player for the Golden State Warriors of the National Basketball Association. He also represents the senior Serbian national basketball team internationally. Bjelica was an All-Euroleague First Team selection as well as the Euroleague MVP in 2015.",
            R.drawable.bjelica
        )
    )

    private lateinit var alertButton: Button

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.titleTextView.text = players[position].title
        holder.binding.descTextView.text = players[position].desc
        holder.binding.imageView.setImageResource(players[position].image)
    }

    override fun getItemCount(): Int {
        return players.size
    }
}