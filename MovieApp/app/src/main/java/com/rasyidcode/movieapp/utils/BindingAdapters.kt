package com.rasyidcode.movieapp.utils

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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

private const val TAG = "BindingAdapters"

@BindingAdapter("posterPath")
fun bindImage(imageView: ImageView, posterPath: String?) {
    if (posterPath == null) {
        imageView.load(R.drawable.placeholder)
    } else {
        posterPath.let {
            val imageUrl = "${BuildConfig.POSTER_BASE_URL}$posterPath"
            val imageUri = imageUrl.toUri().buildUpon().scheme("https").build()
            imageView.load(imageUri) {
                placeholder(R.drawable.placeholder)
            }
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
    textView.text = if (movieTitle.isNullOrEmpty()) {
        "No Title"
    } else {
        movieTitle
    }
}

@BindingAdapter("movieGenres")
fun bindTextViewMovieGenres(textView: TextView, movieGenres: String?) {
    textView.text = if (movieGenres.isNullOrEmpty()) {
        "No Genre"
    } else {
        movieGenres
    }
}

@BindingAdapter("movieOverview")
fun bindTextViewMovieOverview(textView: TextView, movieOverview: String?) {
    textView.text = if (movieOverview.isNullOrEmpty()) {
        "-"
    } else {
        movieOverview
    }
}

@BindingAdapter("movieList")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Movie>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    adapter.submitList(data)
}

@BindingAdapter("isLoading")
fun bindProgressBarMovieLatest(progressBar: ProgressBar, isLoading: Boolean?) {
    isLoading?.let {
        progressBar.visibility = if (it) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

@BindingAdapter("isNetworkError", "movieList", "context")
fun bindNetworkErrorTextView(
    textView: TextView,
    isNetworkError: Boolean?,
    movieList: List<Movie>?,
    context: Context?
) {
    isNetworkError?.let {
        if (isNetworkError && movieList.isNullOrEmpty()) {
            Log.d(TAG, "isNetworkError")
            textView.visibility = View.VISIBLE
            textView.text = context?.getString(R.string.network_error)
        } else {
            textView.visibility = View.GONE
        }
    }
}