package it.fantacalcio.sample.feature_list.data.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.fantacalcio.sample.databinding.ItemPlayersListBinding
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

class PlayersListAdapter(
    initialList: List<PlayerModel> = emptyList()
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
        (holder as PlayersListViewHolder).bind(getItem(position))
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

        fun bind(item: PlayerModel) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                with (binding) {
                    val context: Context


                }
            }
        }

    }



}