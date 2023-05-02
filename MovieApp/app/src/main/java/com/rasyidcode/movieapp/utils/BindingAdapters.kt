package com.rasyidcode.movieapp.utils

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.data.domain.Movie
import com.rasyidcode.movieapp.ui.movie.MovieListAdapter

@BindingAdapter("posterPath")
fun bindImage(imageView: ImageView, posterPath: String?) {
    posterPath?.let {
        val imageUrl = "${BuildConfig.POSTER_BASE_URL}$posterPath"
        val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
        imageView.load(imageUri) {
            placeholder(R.drawable.placeholder)
        }
    }
}

@BindingAdapter("movieRating")
fun bindRating(ratingBar: RatingBar, movieRating: Float?) {
    ratingBar.rating = movieRating?.div(2) ?: 0f
}

@BindingAdapter("movieRating")
fun bindRatingText(textView: TextView, movieRating: Float?) {
    textView.text = (movieRating?.div(2) ?: 0f).toString()
}

@BindingAdapter("movieTitle")
fun bindTextViewMovieTitle(textView: TextView, movieTitle: String?) {
    textView.text = movieTitle ?: "No Title"
}

@BindingAdapter("movieGenres")
fun bindTextViewMovieGenres(textView: TextView, movieGenres: String?) {
    textView.text = movieGenres ?: "No Genre"
}

@BindingAdapter("movieOverview")
fun bindTextViewMovieOverview(textView: TextView, movieOverview: String?) {
    textView.text = movieOverview ?: "-"
}

@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    adapter.submitList(data)
}