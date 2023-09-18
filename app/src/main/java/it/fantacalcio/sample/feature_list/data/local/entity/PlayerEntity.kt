package it.fantacalcio.sample.feature_list.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import java.io.Serializable

@Entity
data class PlayerEntity(

    @PrimaryKey val playerId: Int,
    val playerName: String,
    val imageURL: String,
    val teamAbbreviation: String,
    val gamesPlayed: Int,
    val averageGrade: Double,
    val averageFantaGrade: Double

) : Serializable {

    fun toPlayerModel(isPreferred: Boolean): PlayerModel {
        return PlayerModel(
            playerId = playerId,
            playerName = playerName,
            imageURL = imageURL,
            teamAbbreviation = teamAbbreviation,
            gamesPlayed = gamesPlayed,
            averageGrade = averageGrade,
            averageFantaGrade = averageFantaGrade,
            isPreferred = isPreferred
        )
    }

}