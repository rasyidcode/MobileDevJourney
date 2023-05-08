package com.rasyidcode.movieapp.ui.movie_detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.databinding.ActivityMovieDetailBinding
import com.rasyidcode.movieapp.ui.review_list.ReviewListActivity
import com.rasyidcode.movieapp.ui.review_list.ReviewListAdapter
import com.rasyidcode.movieapp.ui.similar_movie.SimilarMovieListActivity

class MovieDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieDetailBinding

    private val viewModel by viewModels<MovieDetailViewModel> {
        MovieDetailViewModel.Factory(
            movieRepository = (application as MovieApplication).movieRepository
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val movieId: Int = intent?.extras?.getInt(MOVIE_ID)
            ?: throw IllegalArgumentException("Required Movie ID is not defined")
        val movieListType: String = intent?.extras?.getString(MOVIE_LIST_TYPE)
            ?: throw IllegalArgumentException("Required Type is not defined")
        val id: Int = intent?.extras?.getInt(ID)
            ?: throw IllegalArgumentException("Required ID is not defined")

        binding.also {
            it.viewModel = viewModel
            it.lifecycleOwner = this
            it.movieId = movieId
            it.reviewList.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            it.reviewList.adapter = ReviewListAdapter()
            it.onSeeAllListener = SeeAllClickListener({
                startActivity(Intent(this, ReviewListActivity::class.java).apply {
                    putExtra(MOVIE_ID, movieId)
                })
            }, {
                startActivity(Intent(this, SimilarMovieListActivity::class.java).apply {
                    putExtra(MOVIE_ID, movieId)
                })
            })
        }

        viewModel.also {
            it.fetchMovieDetail(
                id = id,
                movieId = movieId,
                movieListType = movieListType
            )

            it.fetchReviews(movieId)
            it.getTheFirstThreeReviews(movieId)?.observe(this) { reviews ->
                Log.d(TAG, "reviewListObserve: ${reviews.size}")
            }

            it.fetchSimilarMovies(movieId)
            it.getMovieDetail(
                id = id,
                movieId = movieId,
                movieListType = movieListType
            )?.observe(this) { movie ->
                movie?.let { notNullMovie ->
                    binding.movie = notNullMovie
                    binding.executePendingBindings()
                }
            }
        }
    }

    companion object {
        const val TAG = "MovieDetailActivity"
        const val ID = "id"
        const val MOVIE_ID = "movie_id"
        const val MOVIE_LIST_TYPE = "movie_list_type"
    }

    class SeeAllClickListener(
        private val onSeeAllReview: () -> Unit,
        private val onSeeAllSimilarMovies: () -> Unit
    ) {

        fun onSeeAllReviewClick(): Unit = onSeeAllReview()

        fun onSeeAllSimilarMoviesClick(): Unit = onSeeAllSimilarMovies()

    }

}