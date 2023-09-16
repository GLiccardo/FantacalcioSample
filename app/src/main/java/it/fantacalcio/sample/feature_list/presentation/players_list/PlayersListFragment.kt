package it.fantacalcio.sample.feature_list.presentation.players_list

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.ui.base.BaseFragment
import it.fantacalcio.sample.databinding.FragmentPlayersListBinding
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersListFragment : BaseFragment<PlayersListViewModel, FragmentPlayersListBinding>(
    R.layout.fragment_players_list,
    PlayersListViewModel::class.java
) {

    companion object {

        val TAG: String = PlayersListFragment::class.java.name

        private const val ARGS_SOMETHING = "args_something"

        fun newInstance(param1: String) =
            PlayersListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_SOMETHING, param1)
                }
            }

    }

    override fun loadData() {
        super.loadData()
        viewModel.getPlayers()
    }

    override fun collectFlows() {
        super.collectFlows()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playersListState.collect { uiState ->
//                    updateUi(uiState)
                }
            }
        }
    }

}