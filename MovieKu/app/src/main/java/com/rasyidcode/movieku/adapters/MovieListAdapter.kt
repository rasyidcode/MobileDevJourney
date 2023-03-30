package com.rasyidcode.movieku.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rasyidcode.movieku.BuildConfig
import com.rasyidcode.movieku.databinding.MovieListItemBinding
import com.rasyidcode.movieku.models.Genre
import com.rasyidcode.movieku.models.Movie

class MovieListAdapter() : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {

    private lateinit var onMovieItemClickListener: OnMovieItemClickListener

    private val genreList = ArrayList<Genre?>()

    val differ = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }

    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MovieListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun onMovieItemClickListener(onMovieItemClickListener: OnMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener
    }

    fun setGenreList(list: List<Genre?>) {
        this.genreList.clear()
        this.genreList.addAll(list)
    }

    inner class ViewHolder(private val binding: MovieListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie?) {
            with(binding) {
                title.text = movie?.originalTitle
                lang.text = movie?.originalLanguage
                releaseDate.text = movie?.releaseDate
                ratingText.text = movie?.voteAverage.toString()
                ratingBar.rating = movie?.voteAverage ?: 0f

                Glide.with(itemView).load("${BuildConfig.PHOTO_BASE_URL}${movie?.posterPath}")
                    .into(poster)

                val map = genreList.associate { it?.id to it?.name }
                val genresString = StringBuilder()

                movie?.genreIds?.let {
                    for (gId in it) {
                        genresString.append("${map[gId]}, ")
                    }
                }

                genre.text = genresString.dropLast(2)

                itemView.setOnClickListener {
                    onMovieItemClickListener.onClick(movie, genresString.toString())
                }
            }
        }

    }
}

interface OnMovieItemClickListener {
    fun onClick(movie: Movie?, genres: String?)
}