package edu.msudenver.cs3013.project03.model

import edu.msudenver.cs3013.project03.model.VenueModel

data class TeamUIModel(

val response: List<TeamAndVenueModel>
)
data class TeamAndVenueModel(
    val team: TeamModel,
    val venue: VenueModel
)



//    val is_national: Boolean,
//    val venue_name: String,
//    val venue_surface: String,
//    val venue_address: String,
//    val venue_city: String,
//    val venue_capacity: Int,


// "team_id": 541,
//                "name": "Real Madrid",
//                "code": null,
//                "logo": "https://media.api-sports.io/football/teams/541.png",
//                "is_national": false,
//                "country": "Spain",
//                "founded": 1902,
//                "venue_name": "Estadio Santiago Bernabéu",
//                "venue_surface": "grass",
//                "venue_address": "Avenida de Concha Espina 1, Chamartín",
//                "venue_city": "Madrid",
//                "venue_capacity": 85454