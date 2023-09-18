package it.fantacalcio.sample.feature_preferred.presentation.preferred_list

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.constants.Constants.EMPTY_STRING
import it.fantacalcio.sample.core.extension.collectLA
import it.fantacalcio.sample.core.ui.base.BaseFragment
import it.fantacalcio.sample.databinding.FragmentPreferredListBinding
import it.fantacalcio.sample.feature_preferred.data.adapter.RVPreferredListAdapter

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

    override fun loadData() {
        super.loadData()
        viewModel.getPreferredPlayers()
    }

    override fun collectFlows() {
        super.collectFlows()
        viewModel.preferredListState.collectLA(viewLifecycleOwner) { uiState ->
            showPreferredPlayersList(uiState)
        }
    }

    private fun showPreferredPlayersList(uiState: PreferredListState) {
        val playersList = uiState.playersList

        if (playersList.isNotEmpty()) {
            binding.rvPreferredList.apply {
                val playersAdapter = RVPreferredListAdapter(playersList)
                adapter = playersAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        } else {
            binding.tvPreferredListEmptyText.text = getString(R.string.search_no_results)
        }
    }

}