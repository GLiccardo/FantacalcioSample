package it.fantacalcio.sample.feature_preferred.presentation.preferred_list

import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

data class PreferredListState(
    val isLoading: Boolean = false,
    val error: String = EMPTY_STRING,
    val playersList: List<PlayerModel> = emptyList()
)
