package it.fantacalcio.sample.feature_list.domain.model

import java.io.Serializable

data class PlayerModel(

    val playerId: Int,
    val playerName: String,
    val imageURL: String,
    val teamAbbreviation: String,
    val gamesPlayed: Int,
    val averageGrade: Double,
    val averageFantaGrade: Double,
    var isPreferred: Boolean

) : Serializable