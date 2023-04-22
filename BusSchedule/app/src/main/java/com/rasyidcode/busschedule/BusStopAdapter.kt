package com.rasyidcode.busschedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.busschedule.database.schedule.Schedule
import com.rasyidcode.busschedule.databinding.BusStopItemBinding
import java.text.SimpleDateFormat
import java.util.Date

class BusStopAdapter(
    private val onItemClicked: (Schedule) -> Unit
) : ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    class BusStopViewHolder(
        private val binding: BusStopItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            binding.stopName.text = schedule.stopName
            binding.arrivalTime.text = SimpleDateFormat(
                "h:mm a"
            ).format(Date(schedule.arrivalTime.toLong() * 1000))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }

        }
    }

}