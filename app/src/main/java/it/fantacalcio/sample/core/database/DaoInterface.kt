package it.fantacalcio.sample.core.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import it.fantacalcio.sample.feature_list.data.local.entity.PlayerEntity
import it.fantacalcio.sample.feature_preferred.data.local.entity.PreferredPlayerEntity

@Dao
interface DaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayers(players: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayer(player: PlayerEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePlayer(player: PlayerEntity)

    @Query("DELETE FROM playerentity")
    fun deleteAllPlayers(): Int

//    @Query("DELETE FROM playerentity WHERE playerId IN(:playerId)")
    @Delete
    fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM playerentity")
    fun getPlayers(): List<PlayerEntity>

//    @Query("SELECT * FROM playerentity WHERE playerId=:playerId")
//    fun getPlayerDetail(playerId: String): PlayerEntity

    /*
     * Preferred players
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPreferredPlayerId(player: PreferredPlayerEntity)

    @Delete
    fun deletePreferredPlayerId(player: PreferredPlayerEntity)

    @Query("SELECT * FROM preferredplayerentity")
    fun getPreferredPlayersId(): List<PreferredPlayerEntity>

}