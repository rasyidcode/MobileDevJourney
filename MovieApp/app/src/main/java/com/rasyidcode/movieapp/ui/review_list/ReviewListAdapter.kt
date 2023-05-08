package com.rasyidcode.movieapp.ui.review_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.movieapp.data.domain.Review
import com.rasyidcode.movieapp.databinding.ReviewListItemBinding

class ReviewListAdapter :
    ListAdapter<Review, ReviewListAdapter.ReviewListViewHolder>(DiffCallback) {

    class ReviewListViewHolder(
        private val binding: ReviewListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(review: Review) {
            binding.review = review
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewListViewHolder {
        return ReviewListViewHolder(
            ReviewListItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ReviewListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Review>() {
            override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
                return oldItem == newItem
            }

        }
    }
}