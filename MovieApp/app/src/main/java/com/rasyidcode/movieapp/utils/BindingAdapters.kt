package com.rasyidcode.movieapp.utils

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.R
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.domain.Genre
import com.rasyidcode.movieapp.data.domain.Movie
import com.rasyidcode.movieapp.data.domain.Review
import com.rasyidcode.movieapp.data.domain.asNamedGenres
import com.rasyidcode.movieapp.ui.movie_detail.MovieDetailActivity
import com.rasyidcode.movieapp.ui.movie_list.MovieListActivity
import com.rasyidcode.movieapp.ui.movie_list.MovieListAdapter
import com.rasyidcode.movieapp.ui.review_list.ReviewListAdapter
import java.math.RoundingMode
import java.text.DecimalFormat

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
    val df = DecimalFormat("#.#").apply { roundingMode = RoundingMode.DOWN }
    val rating = df.format(movieRating?.div(2) ?: 0f)
    ratingBar.rating = rating.toFloat()
}

@BindingAdapter("movieRating")
fun bindRatingText(textView: TextView, movieRating: Float?) {
    val df = DecimalFormat("#.#").apply { roundingMode = RoundingMode.DOWN }
    val rating = df.format(movieRating?.div(2) ?: 0f)
    textView.text = rating
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

@BindingAdapter("movieList", "genres")
fun bindMovieList(recyclerView: RecyclerView, data: List<Movie>?, genres: List<Genre>?) {
    val adapter = recyclerView.adapter as MovieListAdapter
    adapter.submitList(data?.map {
        it.copy(
            genres = it.genres?.asNamedGenres(genres)
        )
    })
}

@BindingAdapter("movieListOnDetail", "context")
fun bindSimilarMovieListOnDetail(
    recyclerView: RecyclerView,
    movies: List<Movie>?,
    context: Context?
) {
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView.adapter = MovieListAdapter(MovieListActivity.OnMovieItemClick { id, movieId ->
        Log.d(TAG, "id: $id")
        Log.d(TAG, "movieId: $movieId")

        if (id != null && movieId != null) {
            context?.startActivity(Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.ID, id)
                putExtra(MovieDetailActivity.MOVIE_ID, movieId)
                putExtra(
                    MovieDetailActivity.MOVIE_LIST_TYPE,
                    MovieListType.SIMILAR.name
                )
            })
        }

    }).apply {
        submitList(movies)
    }
}

@BindingAdapter("reviewList", "context")
fun bindReviewList(recyclerView: RecyclerView, reviews: List<Review>?, context: Context?) {
    recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    recyclerView.adapter = ReviewListAdapter().apply {
        submitList(reviews)
    }
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
            textView.visibility = View.VISIBLE
            textView.text = context?.getString(R.string.network_error)
        } else {
            textView.visibility = View.GONE
        }
    }
}

@BindingAdapter("isFilterApplied")
fun bindFloatingActionButton(
    floatingActionButton: FloatingActionButton,
    isFilterApplied: Boolean?
) {

    floatingActionButton.setImageResource(
        if (isFilterApplied == true) {
            R.drawable.baseline_filter_alt_off_24
        } else {
            R.drawable.baseline_filter_alt_24
        }
    )

}