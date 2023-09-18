package it.fantacalcio.sample.feature_list.data.repository_impl

import it.fantacalcio.sample.core.database.DaoInterface
import it.fantacalcio.sample.core.network.retrofit.ApiInterface
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PlayerRepositoryImpl(
    private val apiInterface: ApiInterface,
    private val daoInterface: DaoInterface
) : PlayerRepository {

    override fun updatePlayer(player: PlayerModel): Flow<ApiResult<Boolean>> =
        flow {
            emit(ApiResult.Loading())
            daoInterface.updatePlayer(player.toPlayerEntity())
            emit(ApiResult.Success(true))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

}