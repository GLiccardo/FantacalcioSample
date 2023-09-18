package it.fantacalcio.sample.feature_list.domain.use_case.player

import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.repository.PlayerRepository
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