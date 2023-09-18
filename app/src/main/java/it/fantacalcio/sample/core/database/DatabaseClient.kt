package it.fantacalcio.sample.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.fantacalcio.sample.feature_list.data.local.entity.PlayerEntity
import it.fantacalcio.sample.feature_preferred.data.local.entity.PreferredPlayerEntity

@Database(
    entities = [PlayerEntity::class, PreferredPlayerEntity::class],
    version = 1
)
abstract class DatabaseClient: RoomDatabase() {

    abstract val dao: DaoInterface

    companion object {

        const val DATABASE_NAME = "fantacalcio_sample_database"

    }

}