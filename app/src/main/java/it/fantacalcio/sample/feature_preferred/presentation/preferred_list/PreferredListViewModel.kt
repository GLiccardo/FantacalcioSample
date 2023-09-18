package it.fantacalcio.sample.feature_preferred.presentation.preferred_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetPreferredPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferredListViewModel @Inject constructor(
    private val getPreferredPlayersUseCase: GetPreferredPlayersUseCase
) : ViewModel() {

    private val _preferredListState = MutableStateFlow(PreferredListState())
    val preferredListState: StateFlow<PreferredListState> = _preferredListState.asStateFlow()

    fun getPreferredPlayers() {
        viewModelScope.launch {
            getPreferredPlayersUseCase().onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _preferredListState.value = PreferredListState(playersList = apiResult.data ?: emptyList())
                    }
                    is ApiResult.Error -> {
                        _preferredListState.value = PreferredListState(error = apiResult.throwable?.localizedMessage ?: "An expected error is occurred")
                    }
                    is ApiResult.Loading -> {
                        _preferredListState.value = PreferredListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

}