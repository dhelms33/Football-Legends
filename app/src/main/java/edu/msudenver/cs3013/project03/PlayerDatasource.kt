package edu.msudenver.cs3013.project03
//datasource to be used in future implementations, originally part of playerrecyclerview
//TODO: add context to this class to link to PlayerRecyclerView.kt
class PlayerDatasource {
    val players: MutableList<Player>
        get() = mutableListOf<Player>(
            Player(
                "Neymar", "Jr", 68,
                5.9, 150.0
            ), Player(
                "Kylian", "Mbappe",
                103, 5.10, 161.0
            ), Player(
                "Erling", "Haaland",
                20, 6.4, 194.0
            ), Player(
                "Cristiano", "Ronaldo",
                760, 6.2, 183.0
            ), Player(
                "Lionel", "Messi",
                644, 5.7, 159.0
            )
        )

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun getPlayer(index: Int): Player {
        return players[index]
    }


}