package it.fantacalcio.sample.feature_preferred.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class PreferredPlayerEntity(

    @PrimaryKey val playerId: Int

) : Serializable