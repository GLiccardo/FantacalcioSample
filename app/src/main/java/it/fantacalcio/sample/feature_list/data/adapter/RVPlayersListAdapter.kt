package it.fantacalcio.sample.feature_list.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.fantacalcio.sample.R
import it.fantacalcio.sample.core.extension.first
import it.fantacalcio.sample.databinding.ItemPlayersListBinding
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

class PlayersListAdapter(
    initialList: List<PlayerModel> = emptyList(),
    private val showHeader: Boolean = false,
    private val onStarClick: (PlayerModel) -> Unit
) : ListAdapter<PlayerModel, RecyclerView.ViewHolder>(diffCallback) {

    var list: List<PlayerModel>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = initialList
    }

    /*
     * DiffUtil
     */

    companion object {

        val diffCallback = object : DiffUtil.ItemCallback<PlayerModel>() {
            override fun areItemsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
                return oldItem.playerId == newItem.playerId
            }

            override fun areContentsTheSame(oldItem: PlayerModel, newItem: PlayerModel): Boolean {
                return oldItem == newItem
            }

        }

    }

    /*
     * Adapter methods
     */

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PlayersListViewHolder(ItemPlayersListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (showHeader) {
            val isHeader = !(position > 0 && getItem(position - 1).playerName.first() == getItem(position).playerName.first())
            (holder as PlayersListViewHolder).bind(getItem(position), true, isHeader)
        } else {
            (holder as PlayersListViewHolder).bind(getItem(position))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /*
     * ViewHolder
     */

    inner class PlayersListViewHolder(
        private val binding: ItemPlayersListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: PlayerModel,
            showHeader: Boolean = false,
            isHeader: Boolean = false
        ) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    val context = root.context

                    // Header: if not first item check if item above has the same header
                    if (showHeader && isHeader) {
                        tvPlayerHeader.text = item.playerName.first()
                        tvPlayerHeader.visibility = View.VISIBLE
                    } else {
                        tvPlayerHeader.visibility = View.GONE
                    }

                    // Icon
                    Glide.with(context)
                        .load(item.imageURL)
                        .into(ivPlayerIcon)

                    // Texts
                    tvPlayerName.text = item.playerName
                    tvPlayerTeam.text = item.teamAbbreviation

                    // Star icon
                    showPreferredIcon(item)

                    // Star click listener
                    ivPlayerPreferred.setOnClickListener {
                        item.isPreferred = !item.isPreferred
                        showPreferredIcon(item)
                        onStarClick(item)
                    }
                }
            }
        }

        private fun showPreferredIcon(item: PlayerModel) {
            if (item.isPreferred) {
                binding.ivPlayerPreferred.setImageResource(R.drawable.ic_star_selected)
            } else {
                binding.ivPlayerPreferred.setImageResource(R.drawable.ic_star_unselected)
            }
        }

    }

}