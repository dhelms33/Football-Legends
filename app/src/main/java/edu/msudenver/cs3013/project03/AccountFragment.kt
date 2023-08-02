package edu.msudenver.cs3013.project03

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

//TODO: make awards fragment and onClickListners for the button that display list of awards
class AccountFragment : Fragment() {
    private var param1: String? = null //factory made params
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) { //onCreate and unused bundles
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, //initalization of fragment
        savedInstanceState: Bundle?
    ): View? {
        // Inflating this layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // gets the arguments passed from the home fragment
        arguments?.let {
            val isChecked = it.getBoolean("isChecked")
            if (isChecked) { //is the checkbox checked?
                // set visibility of textview to visible
                val isCheckedText: TextView? = view.findViewById(R.id.isChecked_text) //if so display that Manchester is your favorite city
                isCheckedText?.visibility = View.VISIBLE
            } //if else do nothing
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AccountFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) = //factory method
            AccountFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}