package edu.msudenver.cs3013.project03
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import edu.msudenver.cs3013.project03.api.APIFootball
import edu.msudenver.cs3013.project03.model.ResponseData
import edu.msudenver.cs3013.project03.model.TeamResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
/**
 * A simple [Fragment] subclass.
 * Use the [TeamsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TeamsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api-football-v1.p.rapidapi.com/v3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }
    private val apiFootball by lazy { retrofit.create(APIFootball::class.java) }

    // private val responseText: TextView by lazy { view?.findViewById<TextView>(R.id.api_response)!! }
    // private val recyclerView: RecyclerView by lazy { view?.findViewById<RecyclerView>(R.id.recycler_view)!! }
    private lateinit var imageLoader: ImageLoader
    private lateinit var recyclerView: RecyclerView
    private lateinit var teamsAdapter: TeamsAdapter

    //    private val TeamsAdapter by lazy {
//        TeamsAdapter(
//            layoutInflater,
//            GlideImageLoader(this),
//            object : TeamsAdapter.OnClickListener {
//                override fun onItemClick(TeamsData: MutableList<TeamResponse>)
//                {
//                }
//            }
//        )
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_teams, container, false)
        recyclerView = view.findViewById(R.id.recycler_view)
        imageLoader = GlideImageLoader(requireContext())
        teamsAdapter = TeamsAdapter(
            layoutInflater,
            imageLoader,
            object : TeamsAdapter.OnClickListener {
                override fun onItemClick(teamData: TeamResponse) {
                    showSelectionDialog(teamData)
                }
            }
        )
        recyclerView.adapter = teamsAdapter

        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        getTeamData()
        return view
    }

    fun getTeamData() {
        val call = apiFootball.getTeams(39, 2021)
        call.enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    val teamList = responseData?.response ?: emptyList()
                    val teamUIList = teamList.map {
                        TeamResponse(
                            id = it.team.id,
                            name = it.team.name,
                            country = it.team.country,
                            founded = it.team.founded,
                            logo = it.team.logo,
                            code = it.team.code,
                            national = it.team.national,

                            )
                    }
                    Log.d("TEAM LIST DATA", teamList.toString())
                    Log.d("TEAM UI LIST DATA", teamUIList.toString())
                    teamsAdapter.setData(teamUIList)
                } else {
                    Log.e("API Error", "Response not successful.")
                }
            }

            override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                Log.e("API Error", t.message.toString())
            }


        })
    }

    private fun showSelectionDialog(teamData: TeamResponse) {
        AlertDialog.Builder(requireContext())
            .setTitle("Team Selected")
            .setMessage("You have selected ${teamData.name}")
            .setPositiveButton("OK") { _, _ -> }
            .show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TeamsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TeamsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}