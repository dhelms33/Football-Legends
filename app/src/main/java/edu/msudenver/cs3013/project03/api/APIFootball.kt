package edu.msudenver.cs3013.project03.api

import edu.msudenver.cs3013.project03.model.ResponseData
import edu.msudenver.cs3013.project03.model.TeamResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Headers
import retrofit2.http.Query

interface APIFootball {
    //headers for apikey and host
    @Headers(
        "x-rapidapi-key: b59308dddfmsh7d291bf380633bbp1e6ce2jsn4a6cf00e5ae3",
        "x-rapidapi-host: api-football-v1.p.rapidapi.com"
            )
    //base URL https://api-football-v1.p.rapidapi.com/v2/
    @GET("teams")
    fun getTeams(
        @Query("league") league: Int,
        @Query("season") season: Int//,
        //@Query("name") name: String,
        //@Query("league") league: Int,
        //@Query("
    ): Call<ResponseData>

}