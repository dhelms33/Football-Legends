package edu.msudenver.cs3013.project03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import edu.msudenver.cs3013.project03.PlayerNameApplication
import edu.msudenver.cs3013.project03.PlayerNameViewModel
import edu.msudenver.cs3013.project03.PlayerTeamViewModel




class PlayerNameFragment : Fragment() {

    private lateinit var preferenceViewModel: PlayerNameViewModel
    private lateinit var preferenceViewModelTeam: PlayerTeamViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_player_name, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up PlayerNameViewModel
        val preferenceWrapper = (requireActivity().application as PlayerNameApplication).playerStore
        preferenceViewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlayerNameViewModel(preferenceWrapper) as T
            }
        }).get(PlayerNameViewModel::class.java)

        preferenceViewModel.textLiveData.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.fragment_player_name_text_view).text = it
        })

        view.findViewById<Button>(R.id.fragment_player_name_button).setOnClickListener {
            val playerName =
                view.findViewById<EditText>(R.id.fragment_player_name_edit_text).text.toString()
            preferenceViewModel.saveText(playerName)
        }

        // Set up PlayerTeamViewModel
        val preferenceWrapperTeam =
            (requireActivity().application as PlayerNameApplication).playerStore
        preferenceViewModelTeam = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PlayerTeamViewModel(preferenceWrapperTeam) as T
            }
        }).get(PlayerTeamViewModel::class.java)

        preferenceViewModelTeam.textLiveData.observe(viewLifecycleOwner, Observer {
            view.findViewById<TextView>(R.id.fragment_player_name_edit_team).text = it
        })

        view.findViewById<Button>(R.id.fragment_player_name_button_team).setOnClickListener {
            val playerTeam =
                view.findViewById<EditText>(R.id.fragment_player_name_edit_team).text.toString()
            preferenceViewModelTeam.saveText(playerTeam)
        }
    }
}