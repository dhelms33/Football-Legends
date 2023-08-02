package edu.msudenver.cs3013.project03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [LeaguesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeaguesFragment : Fragment() { //extend fragment class
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) { //called on creation of fragment
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView( //called on creation of view
        inflater: LayoutInflater, container: ViewGroup?, //infalting inflater, container, and bundle
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_leagues, container, false) //instantiating view to send data to news fragment
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox_leagues) //checkbox instatiation
        val button = view.findViewById<Button>(R.id.navButtonLeagues) //button instantiation

        button?.setOnClickListener{//if clicked
            val isChecked = checkbox.isChecked //if checked
            val bundle = Bundle()
            bundle.putBoolean("isChecked", isChecked) //pass bundle if checked

            // Use the findNavController method to navigate to the ContentFragment
            Navigation.findNavController(it).navigate(R.id.action_nav_leagues_to_news, bundle) //pass bundle with checked to News


        }
        return view
    }

}