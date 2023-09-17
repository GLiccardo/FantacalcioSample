package it.fantacalcio.sample.feature_list.domain.use_case.get_players

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchedPlayerUseCase @Inject constructor(
    private val repository: PlayersRepository
) {

    operator fun invoke(
        playerName: String
    ): Flow<ApiResult<List<PlayerModel>>> {
        if (playerName.isBlank()) {
            return flow {}
        }
        return repository.getSearchedPlayer(playerName)
    }

}