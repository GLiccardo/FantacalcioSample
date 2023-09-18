package it.fantacalcio.sample.feature_preferred.data.repository_impl

import it.fantacalcio.sample.core.database.DaoInterface
import it.fantacalcio.sample.core.network.retrofit.ApiInterface
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_preferred.domain.repository.PlayerRepository
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

            // Controllo se il player selezionato è già un preferito oppure no
            val isPreferred = daoInterface.getPreferredPlayersId().any { it.playerId == player.playerId }

            // Se è già un preferito, rimuovo quell'item dalla tabella, altrimenti lo aggiungo
            if (isPreferred) {
                daoInterface.deletePreferredPlayerId(player.toPreferredPlayerEntity())
            } else {
                daoInterface.addPreferredPlayerId(player.toPreferredPlayerEntity())
            }

            emit(ApiResult.Success(true))
        }.catch { t ->
            emit(ApiResult.Error(t))
        }.flowOn(Dispatchers.IO)

}