package it.fantacalcio.sample.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import it.monaconutrizionista.feature_posts.data.local.entity.PostEntity

@Database(
    entities = [PlayerEntity::class],
    version = 1
)
abstract class DatabaseClient: RoomDatabase() {

    abstract val dao: DaoInterface

    companion object {

        const val DATABASE_NAME = "fantacalcio_sample_database"

    }

}