package it.fantacalcio.sample.feature_list.presentation.players_list

import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

data class PlayersListState(
    val isLoading: Boolean = false,
    val error: String = EMPTY_STRING,
    val playersList: List<PlayerModel> = emptyList()
)
