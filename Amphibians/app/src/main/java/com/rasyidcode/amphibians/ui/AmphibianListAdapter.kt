package com.rasyidcode.amphibians.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.amphibians.databinding.ListViewItemBinding
import com.rasyidcode.amphibians.network.Amphibian

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
class AmphibianListAdapter(
    val clickListener: AmphibianListener
) :
    ListAdapter<Amphibian, AmphibianListAdapter.AmphibianViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AmphibianViewHolder {
        return AmphibianViewHolder(
            ListViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AmphibianViewHolder, position: Int) {
        val amphibian = getItem(position)
        holder.bind(clickListener, amphibian)
    }

    class AmphibianViewHolder(
        private var binding: ListViewItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(clickListener: AmphibianListener, amphibian: Amphibian) {
            binding.amphibian = amphibian
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Amphibian>() {
        override fun areItemsTheSame(oldItem: Amphibian, newItem: Amphibian): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Amphibian, newItem: Amphibian): Boolean {
            return oldItem.type == newItem.type && oldItem.description == newItem.description
        }

    }

}

class AmphibianListener(
    val clickListener: (amphibian: Amphibian) -> Unit
) {
    fun onClick(amphibian: Amphibian) = clickListener(amphibian)
}