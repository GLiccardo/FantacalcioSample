package it.fantacalcio.sample.feature_list.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.fantacalcio.sample.databinding.ItemPlayersListBinding
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

class PlayersListAdapter(
    initialList: List<PlayerModel> = emptyList(),
    private val showHeader: Boolean = false
) : ListAdapter<PlayerModel, RecyclerView.ViewHolder>(diffCallback) {

    var list: List<PlayerModel>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list= initialList
    }

    /*
     * DiffUtil
     */

    companion object {

        val diffCallback = object: DiffUtil.ItemCallback<PlayerModel>() {
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
        val isHeader =
            !(position > 0 && getItem(position - 1).playerName.substring(0, 1) == getItem(position).playerName.substring(0, 1))
        (holder as PlayersListViewHolder).bind(getItem(position), showHeader, isHeader)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /*
     * ViewHolder
     */

    inner class PlayersListViewHolder(
        private val binding: ItemPlayersListBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: PlayerModel,
            showHeader: Boolean = false,
            isHeader: Boolean = false
        ) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                with (binding) {
                    val context = root.context

                    tvPlayerName.text = item.playerName
                    tvPlayerTeam.text = item.teamAbbreviation

                    Glide.with(context)
                        .load(item.imageURL)
                        .into(ivPlayerIcon)

                    // if not first item check if item above has the same header
                    if (showHeader && isHeader) {
                        tvPlayerHeader.text = item.playerName.substring(0, 1)
                        tvPlayerHeader.visibility = View.VISIBLE
                    } else {
                        tvPlayerHeader.visibility = View.GONE
                    }
                }
            }
        }

    }



}