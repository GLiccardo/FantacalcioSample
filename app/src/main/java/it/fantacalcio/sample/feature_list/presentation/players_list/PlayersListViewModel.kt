package it.fantacalcio.sample.feature_list.presentation.players_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetOrderedPlayersUseCase
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetSearchedPlayersUseCase
import it.fantacalcio.sample.feature_list.domain.use_case.player.UpdatePlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(
    private val getOrderedPlayersUseCase: GetOrderedPlayersUseCase,
    private val getSearchedPlayersUseCase: GetSearchedPlayersUseCase,
    private val updatePlayerUseCase: UpdatePlayerUseCase
) : ViewModel() {

    private val _orderedPlayersListState = MutableStateFlow(PlayersListState())
    val orderedPlayersListState: StateFlow<PlayersListState> = _orderedPlayersListState.asStateFlow()

    private val _searchedPlayersListState = MutableStateFlow(PlayersListState())
    val searchedPlayersListState: StateFlow<PlayersListState> = _searchedPlayersListState.asStateFlow()

    private val _preferredListState = MutableStateFlow(false)
    val preferredListState: StateFlow<Boolean> = _preferredListState.asStateFlow()

//    init {
//        getPlayers()
//    }

    fun getOrderedPlayers() {
        viewModelScope.launch {
            getOrderedPlayersUseCase().onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _orderedPlayersListState.value = PlayersListState(playersList = apiResult.data ?: emptyList())
                    }
                    is ApiResult.Error -> {
                        _orderedPlayersListState.value = PlayersListState(error = apiResult.throwable?.localizedMessage ?: "An expected error is occurred")
                    }
                    is ApiResult.Loading -> {
                        _orderedPlayersListState.value = PlayersListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun getSearchedPlayer(nameSubstring: String) {
        viewModelScope.launch {
            getSearchedPlayersUseCase(nameSubstring).onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _searchedPlayersListState.value = PlayersListState(playersList = apiResult.data ?: emptyList())
                    }
                    is ApiResult.Error -> {
                        _searchedPlayersListState.value = PlayersListState(error = apiResult.throwable?.localizedMessage ?: "An expected error is occurred")
                    }
                    is ApiResult.Loading -> {
                        _searchedPlayersListState.value = PlayersListState(isLoading = true)
                    }
                }
            }.launchIn(this)
        }
    }

    fun updatePlayer(
        player: PlayerModel,
        nameSubstring: String? = null
    ) {
        viewModelScope.launch {
            updatePlayerUseCase(player, nameSubstring).onEach { apiResult ->
                when (apiResult) {
                    is ApiResult.Success -> {
                        _preferredListState.value = apiResult.data == true
                    }
                    is ApiResult.Error -> {
                        _preferredListState.value = false
                    }
                    is ApiResult.Loading -> {
                        _preferredListState.value = false
                    }
                }
            }.launchIn(this)
        }
    }

}