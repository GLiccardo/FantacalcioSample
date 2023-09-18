package it.fantacalcio.sample.feature_list.domain.model

import it.fantacalcio.sample.feature_list.data.local.entity.PlayerEntity
import it.fantacalcio.sample.feature_preferred.data.local.entity.PreferredPlayerEntity
import java.io.Serializable

data class PlayerModel(

    val playerId: Int,
    val playerName: String,
    val imageURL: String,
    val teamAbbreviation: String,
    val gamesPlayed: Int,
    val averageGrade: Double,
    val averageFantaGrade: Double,
    var isPreferred: Boolean = false

) : Serializable {

    fun toPlayerEntity(): PlayerEntity {
        return PlayerEntity(
            playerId = playerId,
            playerName = playerName,
            imageURL = imageURL,
            teamAbbreviation = teamAbbreviation,
            gamesPlayed = gamesPlayed,
            averageGrade = averageGrade,
            averageFantaGrade = averageFantaGrade
        )
    }

    fun toPreferredPlayerEntity(): PreferredPlayerEntity {
        return PreferredPlayerEntity(
            playerId = playerId
        )
    }

}