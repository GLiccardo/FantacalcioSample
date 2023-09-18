package it.fantacalcio.sample.feature_list.presentation.preferred_list

import android.os.Bundle
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.core.ui.base.BaseFragment
import it.fantacalcio.sample.databinding.FragmentPreferredListBinding

@AndroidEntryPoint
class PreferredListFragment : BaseFragment<PreferredListViewModel, FragmentPreferredListBinding>(
    R.layout.fragment_preferred_list,
    PreferredListViewModel::class.java
) {

    private lateinit var text: String

    companion object {

        val TAG: String = PreferredListFragment::class.java.name

        private const val ARGS_TEXT = "args_text"

        fun newInstance(text: String) =
            PreferredListFragment().apply {
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
//        binding.ivPlayersListToolbarSearchIcon.setOnClickListener {
//
//        }

        // Close icon
//        binding.ivPlayersListToolbarCloseIcon.setOnClickListener {
//            viewModel.getOrderedPlayers()
//
//        }
    }

    override fun loadData() {
        super.loadData()
//        viewModel.getPreferredPlayers()
    }

    override fun collectFlows() {
        super.collectFlows()
//        viewModel.orderedPlayersListState.collectLA(viewLifecycleOwner) { uiState ->
//            showOrderedPlayersList(uiState)
//        }

//        viewModel.searchedPlayersListState.collectLA(viewLifecycleOwner) { uiState ->
//            hideKeyboard()
//            showSearchedPlayersList(uiState)
//        }
    }

}