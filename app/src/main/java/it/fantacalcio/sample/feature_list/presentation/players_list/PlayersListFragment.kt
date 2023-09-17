package it.fantacalcio.sample.feature_list.presentation.players_list

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.core.ui.base.BaseFragment
import it.fantacalcio.sample.databinding.FragmentPlayersListBinding
import it.fantacalcio.sample.feature_list.data.adapter.PlayersListAdapter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersListFragment : BaseFragment<PlayersListViewModel, FragmentPlayersListBinding>(
    R.layout.fragment_players_list,
    PlayersListViewModel::class.java
) {

    private lateinit var text: String

    companion object {

        val TAG: String = PlayersListFragment::class.java.name

        private const val ARGS_TEXT = "args_text"

        fun newInstance(text: String) =
            PlayersListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGS_TEXT, text)
                }
            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            text = it.getString(ARGS_TEXT, EMPTY_STRING)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPlayerListEmptyText.text = text
    }

    override fun loadData() {
        super.loadData()
        viewModel.getOrderedPlayers()
    }

    override fun collectFlows() {
        super.collectFlows()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.playersListState.collect { uiState ->
                    updateUi(uiState)
                }
            }
        }
    }

    private fun updateUi(uiState: PlayersListState) {
        binding.rvPlayersList.apply {
            val playersList = uiState.playersList
            val playersAdapter = PlayersListAdapter(playersList, true)
            adapter = playersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

}