package it.fantacalcio.sample.feature_list.presentation.players_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetOrderedPlayersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val getOrderedPlayersUseCase: GetOrderedPlayersUseCase
) : ViewModel() {

    private val _playersListState = MutableStateFlow(PlayersListState())
    val playersListState: StateFlow<PlayersListState> = _playersListState.asStateFlow()

//    init {
//        getPlayers()
//    }

    fun getOrderedPlayers() {
        viewModelScope.launch {
            getOrderedPlayersUseCase().onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _playersListState.value = PlayersListState(playersList = apiResult.data ?: emptyList())
                    }
                    is ApiResult.Error -> {
                        _playersListState.value = PlayersListState(error = apiResult.throwable?.localizedMessage ?: "An expected error is occurred")
                    }
                    is ApiResult.Loading -> {
                        _playersListState.value = PlayersListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getSearchedPlayer() {
        viewModelScope.launch {
            getOrderedPlayersUseCase().onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _playersListState.value = PlayersListState(playersList = apiResult.data ?: emptyList())
                    }
                    is ApiResult.Error -> {
                        _playersListState.value = PlayersListState(error = apiResult.throwable?.localizedMessage ?: "An expected error is occurred")
                    }
                    is ApiResult.Loading -> {
                        _playersListState.value = PlayersListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

}