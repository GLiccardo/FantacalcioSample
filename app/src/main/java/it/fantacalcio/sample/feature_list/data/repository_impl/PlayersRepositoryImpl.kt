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

    override fun getPlayers(): Flow<ApiResult<List<PlayerModel>>> =
        flow {
            emit(ApiResult.Loading())

            // Get response result
            val remotePlayerList = apiInterface.getPlayers()

            // Update DB
            daoInterface.deleteAllPlayers()
            daoInterface.insertPlayers(remotePlayerList.map { it.toPlayerEntity() })

            // Return local model
            val localPlayerList = daoInterface.getPlayers().map { it.toPlayerModel() }
            emit(ApiResult.Success(localPlayerList))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

}