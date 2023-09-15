package it.fantacalcio.sample.core.network.retrofit

import it.fantacalcio.sample.feature_list.data.remote.response.PlayerResponse
import retrofit2.http.GET

interface ApiInterface {

    @GET("/test")
    suspend fun getPlayers(): List<PlayerResponse>

}