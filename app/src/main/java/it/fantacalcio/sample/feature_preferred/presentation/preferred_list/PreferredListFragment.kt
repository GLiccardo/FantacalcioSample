package it.fantacalcio.sample.feature_preferred.presentation.preferred_list

import android.os.Bundle
import android.view.View
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

    private lateinit var errorText: String

    val preferredAdapter: RVPreferredListAdapter by lazy {
        RVPreferredListAdapter(emptyList())
    }

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
            errorText = it.getString(ARGS_TEXT, EMPTY_STRING)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRV()
        setupListeners()
    }

    private fun setupRV() {
        binding.rvPreferredList.apply {
            adapter = preferredAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setupListeners() {
        // Search icon
        binding.cvUnlockPremium.setOnClickListener {
            viewModel.unlockPremium()
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

        viewModel.isPremiumActive.collectLA(viewLifecycleOwner) { isPremiumActive ->
            updateListContent(isPremiumActive)
            showPremiumButton(isPremiumActive)
        }
    }

    private fun updateListContent(isPremiumActive: Boolean) {
        preferredAdapter.updateContent(isPremiumActive)
        preferredAdapter.notifyItemRangeChanged(0, preferredAdapter.itemCount)
    }

    private fun showPremiumButton(isPremiumActive: Boolean) {
        if (isPremiumActive) {
            binding.cvUnlockPremium.visibility = View.GONE
        } else {
            binding.cvUnlockPremium.visibility = View.VISIBLE
        }
    }

    private fun showPreferredPlayersList(uiState: PreferredListState) {
        val playersList = uiState.playersList

        if (playersList.isNotEmpty()) {
            binding.clPreferredListHeader.visibility = View.VISIBLE
            binding.rvPreferredList.visibility = View.VISIBLE
            binding.tvPreferredListEmptyText.visibility = View.GONE
            preferredAdapter.list = playersList
        } else {
            binding.clPreferredListHeader.visibility = View.GONE
            binding.rvPreferredList.visibility = View.GONE
            binding.tvPreferredListEmptyText.visibility = View.VISIBLE
            binding.tvPreferredListEmptyText.text = errorText
        }
    }

}