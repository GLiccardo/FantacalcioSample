package it.fantacalcio.sample.core.network.retrofit

import retrofit2.http.GET

interface ApiInterface {

    @GET("/test")
    suspend fun getPlayers(): List<PlayerResponse>

}