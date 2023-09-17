package it.fantacalcio.sample.feature_list.presentation.players_list

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.core.extension.collectLA
import it.fantacalcio.sample.core.extension.hideKeyboard
import it.fantacalcio.sample.core.ui.base.BaseFragment
import it.fantacalcio.sample.databinding.FragmentPlayersListBinding
import it.fantacalcio.sample.feature_list.data.adapter.PlayersListAdapter

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
        setupListeners()
    }

    private fun setupListeners() {
        // Search icon
        binding.ivPlayersListToolbarSearchIcon.setOnClickListener {
            val text = binding.etPlayerListToolbarSearchText.text.toString()
            if (text.isNotEmpty()) {
                viewModel.getSearchedPlayer(text)
            }
            showCloseIcon()
        }

        // Close icon
        binding.ivPlayersListToolbarCloseIcon.setOnClickListener {
            viewModel.getOrderedPlayers()
            binding.etPlayerListToolbarSearchText.text?.clear()
            showSearchIcon()
        }
    }

    override fun loadData() {
        super.loadData()
        viewModel.getOrderedPlayers()
    }

    override fun collectFlows() {
        super.collectFlows()
        viewModel.orderedPlayersListState.collectLA(viewLifecycleOwner) { uiState ->
            showOrderedPlayersList(uiState)
        }

        viewModel.searchedPlayersListState.collectLA(viewLifecycleOwner) { uiState ->
            hideKeyboard()
            showSearchedPlayersList(uiState)
        }
    }

    private fun showOrderedPlayersList(uiState: PlayersListState) {
        binding.rvPlayersList.apply {
            val playersList = uiState.playersList
            val playersAdapter = PlayersListAdapter(playersList, true)
            adapter = playersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun showSearchedPlayersList(uiState: PlayersListState) {
        val playersList = uiState.playersList

        if (playersList.isNotEmpty()) {
            binding.rvPlayersList.apply {
                val playersAdapter = PlayersListAdapter(playersList, false)
                adapter = playersAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } else {
            binding.tvPlayerListEmptyText.text = getString(R.string.search_no_results)
        }
    }

    private fun showSearchIcon() {
        binding.ivPlayersListToolbarSearchIcon.visibility = View.VISIBLE
        binding.ivPlayersListToolbarCloseIcon.visibility = View.GONE
    }

    private fun showCloseIcon() {
        binding.ivPlayersListToolbarSearchIcon.visibility = View.GONE
        binding.ivPlayersListToolbarCloseIcon.visibility = View.VISIBLE
    }

}