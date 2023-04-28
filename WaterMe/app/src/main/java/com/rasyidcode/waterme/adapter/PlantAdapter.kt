package com.rasyidcode.waterme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.waterme.databinding.ListItemBinding
import com.rasyidcode.waterme.model.Plant

class PlantAdapter(
    private val longClickListener: PlantListener
) : ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallback) {

    class PlantViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            longClickListener: PlantListener,
            plant: Plant
        ) {
            binding.plant = plant
            binding.longClickListener = longClickListener
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlantViewHolder {
        return PlantViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        holder.bind(
            longClickListener = longClickListener,
            plant = getItem(position)
        )
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.name == newItem.name
        }

    }

}

class PlantListener(
    val longClickListener: (plant: Plant) -> Boolean
) {
    fun onLongClick(plant: Plant) = longClickListener(plant)
}