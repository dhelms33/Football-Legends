package edu.msudenver.cs3013.project03
//home menu directs you to your account hub and directs the users to the menus
//hamburger menu is slightly obscured by the background but still in the top left corner

//TODO: add scrolling images and gifs of mancity that can be retrieved from soccer API
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

class HomeFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) { //onCreate is called when the fragment is created
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, //initalization of inflater which the layout for the fragment
        container: ViewGroup?, //initalization of container
        savedInstanceState: Bundle? //initalization of bundle
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false) //inflation
        val checkbox = view.findViewById<CheckBox>(R.id.checkbox_home) //initalization of checkbox
        val button = view.findViewById<Button>(R.id.navButton) //initalization of button
        val button2 = view.findViewById<Button>(R.id.navButton2)
        val button3 = view.findViewById<Button>(R.id.navButton3)

        button?.setOnClickListener { //if clicked
            val isChecked = checkbox.isChecked //see if this checbox is checked
            val bundle = Bundle()
            bundle.putBoolean(
                "isChecked",
                isChecked
            ) //if checked pass the boolean isChecked to the bundle

            // Use the findNavController method to navigate to the ContentFragment
            Navigation.findNavController(it)
                .navigate(R.id.action_nav_home_to_account, bundle) //pass the bundle to account


        }
        //from home to maps
        button2.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_maps)
        }
        //from home to create
        button3.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_nav_home_to_create)
        }


        return view //return the view
    }
}