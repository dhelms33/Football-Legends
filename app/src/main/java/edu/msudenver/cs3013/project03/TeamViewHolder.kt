package edu.msudenver.cs3013.project03

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.msudenver.cs3013.project03.model.TeamResponse
import edu.msudenver.cs3013.project03.model.TeamUIModel

class TeamViewHolder( //initializes response that is used in recyclerview
    private val containerView: View,
    private val imageLoader: ImageLoader,
    private val onClickListener: OnClickListener
) : RecyclerView.ViewHolder(containerView) {
    private val teamNameView: TextView
            by lazy { containerView.findViewById(R.id.item_team_name) }
    private val teamCountryView: TextView
            by lazy { containerView.findViewById(R.id.item_team_country) }
    private val teamVenueView: TextView
            by lazy { containerView.findViewById(R.id.item_team_founded) }
    private val teamPhotoView: ImageView
            by lazy { containerView.findViewById(R.id.item_team_logo) }

    fun bindData(teamData: TeamResponse) {
        containerView.setOnClickListener { onClickListener.onClick(teamData) }
        imageLoader.loadImage(teamData.logo, teamPhotoView)
        teamNameView.text = teamData.name
        teamCountryView.text = teamData.country
    }
//        teamVenueView.text = teamData.venue_name
//        teamVenueSurfaceView.text =  teamData.venue_surface


    interface OnClickListener {
        fun onClick(teamData: TeamResponse)
    }
}





