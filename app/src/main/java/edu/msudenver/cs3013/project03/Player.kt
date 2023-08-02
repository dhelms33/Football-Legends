package edu.msudenver.cs3013.project03
//I originally wanted to pass data out of this class and into fragments, and this was the class that would take said data and even allow people to enter players into the database.
// I couldn't figure out how to do this though.
//TODO: call this in SecondActivity.kt and make it so that it can pass data to the fragments
data class Player (val firstName: String, val lastName: String, val numOfGoals: Int,
                   val height: Double, val weight: Double) {
}