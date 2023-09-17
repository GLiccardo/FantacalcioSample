package it.fantacalcio.sample.core.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.fantacalcio.sample.FantacalcioSampleApplication
import it.fantacalcio.sample.core.database.DatabaseClient
import it.fantacalcio.sample.core.network.retrofit.ApiClient
import it.fantacalcio.sample.core.network.retrofit.ApiInterface
import it.fantacalcio.sample.feature_list.data.repository_impl.PlayersRepositoryImpl
import it.fantacalcio.sample.feature_list.domain.repository.PlayersRepository
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetOrderedPlayersUseCase
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetSearchedPlayersUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(@ApplicationContext app: Context): FantacalcioSampleApplication {
        return app as FantacalcioSampleApplication
    }

    @Provides
    @Singleton
    fun provideContext(application: FantacalcioSampleApplication): Context {
        return application.applicationContext
    }

    /*
     * Data Sources
     */

    @Provides
    @Singleton
    fun provideApiInterface(app: Application): ApiInterface {
        return ApiClient.getRetrofitApi(app)
    }

    @Provides
    @Singleton
    fun provideDatabase(app: Application): DatabaseClient {
        return Room.databaseBuilder(
            app,
            DatabaseClient::class.java,
            DatabaseClient.DATABASE_NAME
        ).build()
    }

    /*
     * Repository
     */

    @Provides
    @Singleton
    fun providePlayersRepository(
        api: ApiInterface,
        db: DatabaseClient
    ): PlayersRepository {
        return PlayersRepositoryImpl(api, db.dao)
    }

    /*
     * Use Cases
     */

    @Provides
    @Singleton
    fun provideGetOrderedPlayersUseCase(repository: PlayersRepository): GetOrderedPlayersUseCase {
        return GetOrderedPlayersUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetSearchedPlayersUseCase(repository: PlayersRepository): GetSearchedPlayersUseCase {
        return GetSearchedPlayersUseCase(repository)
    }

}