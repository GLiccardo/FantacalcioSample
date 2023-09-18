package it.fantacalcio.sample.feature_list.data.repository_impl

import it.fantacalcio.sample.core.database.DaoInterface
import it.fantacalcio.sample.core.network.retrofit.ApiInterface
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlayersRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val daoInterface: DaoInterface
) : PlayersRepository {

    override fun getOrderedPlayers(): Flow<ApiResult<List<PlayerModel>>> =
        flow {
            emit(ApiResult.Loading())

//            daoInterface.deleteAllPlayers()

            // Get database results
            val databasePlayerList = daoInterface.getPlayers()

            val localPlayersList =
                if (databasePlayerList.isEmpty()) {
                    // Get remote results
                    val remotePlayerList = apiInterface.getPlayers()
                    // Update DB
                    daoInterface.deleteAllPlayers()
                    daoInterface.insertPlayers(remotePlayerList.map { it.toPlayerEntity() })
                    daoInterface.getPlayers().map { it.toPlayerModel() }
                } else {
                    databasePlayerList.map { it.toPlayerModel() }
                }

            // Return oredered results
            val localPlayersListOrdered = localPlayersList.sortedBy { it.playerName }
            emit(ApiResult.Success(localPlayersListOrdered))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

    override fun getSearchedPlayers(playerName: String): Flow<ApiResult<List<PlayerModel>>> =
        flow {
            emit(ApiResult.Loading())

            val playerList = daoInterface.getPlayers().map { it.toPlayerModel() }
            val results = playerList.filter { player -> player.playerName.contains(playerName, true) } // TODO: forse startsWith?
            val orderedResults = results.sortedBy { it.playerName }
            emit(ApiResult.Success(orderedResults))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

    override fun getPreferredPlayers(): Flow<ApiResult<List<PlayerModel>>> =
        flow {
            emit(ApiResult.Loading())

            // Get database result
            val playersList = daoInterface.getPlayers().map { it.toPlayerModel() }

            // Filter preferred players
            val preferredPlayers = playersList.filter { it.isPreferred }

            // Return ordered results
            val preferredPlayersOrdered = preferredPlayers.sortedBy { it.playerName }
            emit(ApiResult.Success(preferredPlayersOrdered))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

}