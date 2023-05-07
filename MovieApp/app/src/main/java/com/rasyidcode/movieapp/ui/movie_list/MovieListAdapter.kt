package com.rasyidcode.movieapp.ui.movie_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rasyidcode.movieapp.data.domain.Movie
import com.rasyidcode.movieapp.databinding.MovieListItemBinding

class MovieListAdapter(
    private val onMovieItemClick: MovieListActivity.OnMovieItemClick
) : ListAdapter<Movie, MovieListAdapter.MovieListViewHolder>(DiffCallback) {

    class MovieListViewHolder(
        private val binding: MovieListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie, onMovieItemClick: MovieListActivity.OnMovieItemClick) {
            binding.movie = movie
            binding.movieItemClick = onMovieItemClick
            binding.id = movie.id
            binding.movieId = movie.movieId
            binding.executePendingBindings()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListViewHolder {
        return MovieListViewHolder(
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        holder.bind(getItem(position), onMovieItemClick)
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }

        }
    }

}