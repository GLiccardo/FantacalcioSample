package it.fantacalcio.sample.feature_preferred.presentation.preferred_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import it.fantacalcio.sample.core.network.utils.ApiResult
import it.fantacalcio.sample.core.sharedpref.SharedPrefManager
import it.fantacalcio.sample.feature_list.domain.use_case.get_players.GetPreferredPlayersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreferredListViewModel @Inject constructor(
    private val sharedPref: SharedPrefManager,
    private val getPreferredPlayersUseCase: GetPreferredPlayersUseCase
) : ViewModel() {

    private val _preferredListState = MutableStateFlow(PreferredListState())
    val preferredListState: StateFlow<PreferredListState> = _preferredListState.asStateFlow()

    private val _isPremiumActive = MutableStateFlow(false)
    val isPremiumActive: StateFlow<Boolean> = _isPremiumActive.asStateFlow()

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

    fun unlockPremium() {
        // Update flow
        _isPremiumActive.value = true

        // Persistence timestamp
        val premiumTimestampMillis = System.currentTimeMillis()
        sharedPref.saveLong("timestampPremium", premiumTimestampMillis)

        // Start timer
        viewModelScope.launch(Dispatchers.IO) {
            val delayDurationMillis = 5 * 1000
            delay(delayDurationMillis.toLong())
            _isPremiumActive.value = false
        }
    }

}