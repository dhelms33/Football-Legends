package edu.msudenver.cs3013.project03.model

data class TeamResponse(
    val id: Int,
    val name: String,
    val code: String?, //should be null??
    val country: String,
//    val imageUrl: String,
    val founded: Int,
    val national: Boolean,
    val logo: String

)
