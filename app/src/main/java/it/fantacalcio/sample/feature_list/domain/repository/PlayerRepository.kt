package it.fantacalcio.sample.feature_list.domain.repository

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import kotlinx.coroutines.flow.Flow

interface PlayerRepository {

    fun updatePlayer(player: PlayerModel): Flow<ApiResult<List<PlayerModel>>>

}