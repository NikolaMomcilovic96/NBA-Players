package com.raywenderlich.nbaplayers.ui.main

import android.app.AlertDialog
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.raywenderlich.nbaplayers.PlayerDetailActivity
import com.raywenderlich.nbaplayers.R
import com.raywenderlich.nbaplayers.databinding.MainFragmentBinding

class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    val players = arrayListOf(
        Model(
            "Luka Doncic",
            "Luka Dončić is a Slovenian professional basketball player for the Dallas Mavericks of the National Basketball Association. He also represents the Slovenian national team.",
            R.drawable.doncic,
            "2.01 m",
            "104 kg",
            "Dallas Mavericks"
        ),
        Model(
            "Ognjen Jaramaz",
            "Ognjen Jaramaz is a Serbian professional basketball player for Bayern Munich of the Basketball Bundesliga and the EuroLeague. Standing at 1.93 m, he plays the shooting guard position.",
            R.drawable.jaramaz,
            "1.93 m",
            "88 kg",
            "Bayern Munich"
        ),
        Model(
            "Nikola Jokic",
            "Nikola Jokić is a Serbian professional basketball player for the Denver Nuggets of the National Basketball Association who plays the center position. The three-time NBA All-Star has been named to the All-NBA Team on three occasions, and has won the NBA Most Valuable Player Award for the 2020–21 NBA season.",
            R.drawable.jokic,
            "2.11 m",
            "129 kg",
            "Denver Nuggets"
        ),
        Model(
            "Nemanja Bjelica",
            "Nemanja Bjelica is a Serbian professional basketball player for the Golden State Warriors of the National Basketball Association. He also represents the senior Serbian national basketball team internationally. Bjelica was an All-Euroleague First Team selection as well as the Euroleague MVP in 2015.",
            R.drawable.bjelica,
            "2.08 m",
            "106 kg",
            "Golden State Warriors"
        )
    )

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = ListSelectionRecyclerViewAdapter(players) { model, clicked ->

            when (clicked) {
                Enum.Alert -> showAlert(model.title, model.desc)
                Enum.Card -> playerActivity(model)
            }
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun showAlert(name: String, description: String) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(description).setNeutralButton(R.string.alert_button) { dialog, _ ->
            dialog.dismiss()
        }
        val alert = dialogBuilder.create()
        alert.setTitle(name)
        alert.show()
    }

    private fun playerActivity(player: Model) {
        startActivity(
            Intent(activity, PlayerDetailActivity::class.java).apply {
                putExtra("title", player.title)
                putExtra("desc", player.desc)
                putExtra("image", player.image)
                putExtra("height", player.height)
                putExtra("weight", player.weight)
                putExtra("club", player.club)
            }
        )
    }
}