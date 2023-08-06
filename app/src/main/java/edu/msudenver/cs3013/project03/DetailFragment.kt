package edu.msudenver.cs3013.project03


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DetailFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) { //oncreate
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false) //inflate xml
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareViewModel()
    }

    private fun prepareViewModel() {
        // Create a locationViewModel reference
        val locationViewModel = ViewModelProvider(requireActivity()).get(LocationViewModel::class.java)

        // Set up an observer for the location property of the locationViewModel
        locationViewModel.location.observe(viewLifecycleOwner, Observer { location -> //get location from livedata viewmodel using an observer
            // Call the updateText method with the location parameter
            updateText(location)
        })
    }

    private fun updateText(location: String) {
        // Update the text of the user_location_tv TextView with the location parameter
        view?.findViewById<TextView>(R.id.location_text)?.text = location //put this data in a textview
    }

    companion object { //factory companion object
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
