package com.rasyidcode.remarsphotos_dotaheroes.herolist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.remarsphotos_dotaheroes.databinding.HeroListItemBinding
import com.rasyidcode.remarsphotos_dotaheroes.network.DotaHero

class HeroListAdapter : ListAdapter<DotaHero, HeroListAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: HeroListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hero: DotaHero) {
            binding.hero = hero
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            HeroListItemBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DotaHero>() {
        override fun areItemsTheSame(oldItem: DotaHero, newItem: DotaHero): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: DotaHero, newItem: DotaHero): Boolean {
            return oldItem.id == newItem.id
        }

    }

}