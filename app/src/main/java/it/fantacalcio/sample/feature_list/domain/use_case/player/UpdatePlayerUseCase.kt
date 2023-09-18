package it.fantacalcio.sample.feature_list.domain.use_case.player

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayerRepository
import it.fantacalcio.sample.feature_list.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePlayerUseCase @Inject constructor(
    private val playerRepo: PlayerRepository,
    private val playersRepo: PlayersRepository
) {

    operator fun invoke(
        player: PlayerModel,
        playerName: String? = null
    ): Flow<ApiResult<List<PlayerModel>>> {
        playerRepo.updatePlayer(player)
        return if (playerName.isNullOrEmpty()) {
            playersRepo.getOrderedPlayers()
        } else {
            playersRepo.getSearchedPlayers(playerName)
        }
    }

}