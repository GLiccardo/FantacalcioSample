package it.fantacalcio.sample.feature_list.domain.use_case.get_players

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderedPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository
) {

    operator fun invoke(): Flow<ApiResult<List<PlayerModel>>> {
        return repository.getOrderedPlayers()
    }

}