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
import it.fantacalcio.sample.feature_list.data.adapter.RVPlayersListAdapter
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel
import timber.log.Timber

@AndroidEntryPoint
class PlayersListFragment : BaseFragment<PlayersListViewModel, FragmentPlayersListBinding>(
    R.layout.fragment_players_list,
    PlayersListViewModel::class.java
) {

    private lateinit var errorText: String

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
            errorText = it.getString(ARGS_TEXT, EMPTY_STRING)
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

        viewModel.preferredListState.collectLA(viewLifecycleOwner) { uiState ->
            if (uiState) {
                Timber.d("Player aggiornato nella lista dei preferiti")
            }
        }
    }

    private fun showOrderedPlayersList(uiState: PlayersListState) {
        binding.rvPlayersList.visibility = View.VISIBLE
        binding.tvPlayerListEmptyText.visibility = View.GONE
        binding.rvPlayersList.apply {
            val playersList = uiState.playersList
            val playersAdapter = RVPlayersListAdapter(playersList, true) { onStarClick(it) }
            adapter = playersAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun onStarClick(item: PlayerModel) {
        viewModel.updatePlayer(item)
    }

    private fun showSearchedPlayersList(uiState: PlayersListState) {
        val playersList = uiState.playersList

        if (playersList.isNotEmpty()) {
            binding.rvPlayersList.visibility = View.VISIBLE
            binding.tvPlayerListEmptyText.visibility = View.GONE
            binding.rvPlayersList.apply {
                val playersAdapter = RVPlayersListAdapter(playersList, false) { onStarClick(it) }
                adapter = playersAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } else {
            binding.rvPlayersList.visibility = View.GONE
            binding.tvPlayerListEmptyText.visibility = View.VISIBLE
            binding.tvPlayerListEmptyText.text = errorText
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