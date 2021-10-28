package com.raywenderlich.nbaplayers.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.raywenderlich.nbaplayers.databinding.ListSelectionViewHolderBinding

class ListSelectionRecyclerViewAdapter(
    private val players: ArrayList<Player>,
    private val onPlayerClickListener: (Player, ListItemClick)->Unit
) :
    RecyclerView.Adapter<ListSelectionViewHolder>() {

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

        holder.binding.cardView.setOnClickListener {
            onPlayerClickListener(players[position], ListItemClick.Card)
        }

        holder.binding.alertButton.setOnClickListener {
            onPlayerClickListener(players[position], ListItemClick.Alert)
        }
    }

    override fun getItemCount(): Int {
        return players.size
    }
}