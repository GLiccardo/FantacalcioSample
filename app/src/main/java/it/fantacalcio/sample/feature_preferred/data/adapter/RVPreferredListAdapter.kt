package it.fantacalcio.sample.feature_preferred.data.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import it.fantacalcio.sample.databinding.ItemPreferredListBinding
import it.fantacalcio.sample.feature_list.domain.model.PlayerModel

class RVPreferredListAdapter(
    initialList: List<PlayerModel> = emptyList(),
    private var isPremiumActive: Boolean = false
) : ListAdapter<PlayerModel, RecyclerView.ViewHolder>(diffCallback) {

    var list: List<PlayerModel>
        get() = currentList
        set(value) {
            submitList(value)
        }

    init {
        list = initialList
    }

    fun updateContent(isPremiumActive: Boolean) {
        this.isPremiumActive = isPremiumActive
//        submitList(list)
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
        return PreferredListViewHolder(ItemPreferredListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as PreferredListViewHolder).bind(getItem(position))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    /*
     * ViewHolder
     */

    inner class PreferredListViewHolder(
        private val binding: ItemPreferredListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayerModel) {
            if (bindingAdapterPosition != RecyclerView.NO_POSITION) {
                with(binding) {
                    val context = root.context

                    // Icon
                    Glide.with(context)
                        .load(item.imageURL)
                        .into(ivPreferredIcon)

                    // Texts
                    tvPreferredName.text = item.playerName
                    tvPreferredTeam.text = item.teamAbbreviation
                    tvPreferredListHeaderPg.text = item.gamesPlayed.toString()
                    tvPreferredListHeaderMv.text = item.averageGrade.toString()
                    tvPreferredListHeaderMfv.text = item.averageFantaGrade.toString()

                    // Visibility
                    if (isPremiumActive) {
                        tvPreferredListHeaderPg.visibility = View.VISIBLE
                        tvPreferredListHeaderMv.visibility = View.VISIBLE
                        tvPreferredListHeaderMfv.visibility = View.VISIBLE
                        ivPreferredListHeaderPg.visibility = View.INVISIBLE
                        ivPreferredListHeaderMv.visibility = View.INVISIBLE
                        ivPreferredListHeaderMfv.visibility = View.INVISIBLE
                    } else {
                        ivPreferredListHeaderPg.visibility = View.VISIBLE
                        ivPreferredListHeaderMv.visibility = View.VISIBLE
                        ivPreferredListHeaderMfv.visibility = View.VISIBLE
                        tvPreferredListHeaderPg.visibility = View.INVISIBLE
                        tvPreferredListHeaderMv.visibility = View.INVISIBLE
                        tvPreferredListHeaderMfv.visibility = View.INVISIBLE
                    }
                }
            }
        }

    }

}