package it.fantacalcio.sample.feature_list.data.remote.response

import com.google.gson.annotations.SerializedName
import it.fantacalcio.sample.feature_list.data.local.entity.PlayerEntity
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import java.io.Serializable

data class PlayerResponse(

    @SerializedName("playerId")
    val playerId: Int,
    @SerializedName("playerName")
    val playerName: String,
    @SerializedName("imageURL")
    val imageURL: String,
    @SerializedName("teamAbbreviation")
    val teamAbbreviation: String,
    @SerializedName("gamesPlayed")
    val gamesPlayed: Int,
    @SerializedName("averageGrade")
    val averageGrade: Double,
    @SerializedName("averageFantaGrade")
    val averageFantaGrade: Double

) : Serializable {

    fun toPlayerModel(): PlayerModel {
        return PlayerModel(
            playerId = playerId,
            playerName = playerName,
            imageURL = imageURL,
            teamAbbreviation = teamAbbreviation,
            gamesPlayed = gamesPlayed,
            averageGrade = averageGrade,
            averageFantaGrade = averageFantaGrade
        )
    }

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

}