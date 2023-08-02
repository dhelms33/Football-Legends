package edu.msudenver.cs3013.project03

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import edu.msudenver.cs3013.project03.model.TeamResponse

class TeamsAdapter(
    private val layoutInflater: LayoutInflater,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.Adapter<TeamViewHolder>() {
    private val teamsData = mutableListOf<TeamResponse>()

    fun setData(newTeamsData: List<TeamResponse>) {
        teamsData.clear()
        teamsData.addAll(newTeamsData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val view = layoutInflater.inflate(R.layout.item_team, parent, false)
        return TeamViewHolder(view, imageLoader, object : TeamViewHolder.OnClickListener {
            override fun onClick(teamData: TeamResponse) = onClickListener.onItemClick(teamData)
        })
    }

//    private fun showSelectionDialog(team: TeamUIModel) {
//        AlertDialog.Builder(layoutInflater.context)
//            .setTitle("Team Selected")
//            .setMessage("You have selected ${team.name}")
//            .setPositiveButton("OK") { _, _ -> }.show()
//    }

    override fun getItemCount() = teamsData.size


    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.bindData(teamsData[position])
    }

    interface OnClickListener {
        fun onItemClick(teamData: TeamResponse)
    }
}
