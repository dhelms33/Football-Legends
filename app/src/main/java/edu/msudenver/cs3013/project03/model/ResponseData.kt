package edu.msudenver.cs3013.project03.model

import edu.msudenver.cs3013.project03.model.TeamResponse
import edu.msudenver.cs3013.project03.model.VenueResponse

data class ResponseData(
    val response: List<TeamAndVenueData>
)
data class TeamAndVenueData(
    val team: TeamResponse,
    val venue: VenueResponse
)
