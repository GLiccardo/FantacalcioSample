package it.fantacalcio.sample.feature_preferred.domain.use_case.player

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_preferred.domain.repository.PlayerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UpdatePlayerUseCase @Inject constructor(
    private val playerRepo: PlayerRepository
) {

    operator fun invoke(
        player: PlayerModel,
        playerName: String? = null
    ): Flow<ApiResult<Boolean>> {
        return playerRepo.updatePlayer(player)
    }

}