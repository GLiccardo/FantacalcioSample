package it.fantacalcio.sample.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.fantacalcio.sample.feature_list.data.local.entity.PlayerEntity

@Dao
interface DaoInterface {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayers(posts: List<PlayerEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(post: PlayerEntity)

    @Query("DELETE FROM playerentity")
    suspend fun deleteAllPlayers()

    @Query("DELETE FROM playerentity WHERE id IN(:playerIdList)")
    suspend fun deleteMultiplePlayers(playerIdList: List<String>)

    @Query("DELETE FROM playerentity WHERE id IN(:playerId)")
    suspend fun deleteSinglePlayer(playerId: String)

    @Query("SELECT * FROM playerentity")
    suspend fun getPlayers(): List<PlayerEntity>

    @Query("SELECT * FROM playerentity WHERE id=:playerId")
    suspend fun getPlayerDetail(playerId: String): PlayerEntity

}