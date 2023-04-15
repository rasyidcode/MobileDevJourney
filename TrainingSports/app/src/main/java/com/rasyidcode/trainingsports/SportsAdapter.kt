package com.rasyidcode.trainingsports

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rasyidcode.trainingsports.databinding.SportsListItemBinding
import com.rasyidcode.trainingsports.model.Sport

class SportsAdapter(
    private val onItemClicked: (Sport) -> Unit
) : ListAdapter<Sport, SportsAdapter.SportsViewHolder>(DiffCallback) {

    private lateinit var context: Context

    class SportsViewHolder(private val binding: SportsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(sport: Sport, context: Context) {
            binding.title.text = context.getString(sport.titleResourceId)
            binding.subTitle.text = context.getString(sport.subtitleResourceId)
            binding.sportsImage.load(sport.imageResourceId)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SportsViewHolder {
        context = parent.context
        return SportsViewHolder(
            SportsListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SportsViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current, context)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Sport>() {
            override fun areItemsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return (oldItem.id == newItem.id || oldItem.titleResourceId == newItem.titleResourceId || oldItem.subtitleResourceId == newItem.subtitleResourceId)
            }

            override fun areContentsTheSame(oldItem: Sport, newItem: Sport): Boolean {
                return oldItem == newItem
            }
        }
    }

}