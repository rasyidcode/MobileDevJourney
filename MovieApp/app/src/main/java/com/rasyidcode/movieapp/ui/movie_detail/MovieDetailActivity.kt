package com.rasyidcode.movieapp.ui.movie_detail

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.databinding.ActivityMovieDetailBinding
import com.rasyidcode.movieapp.ui.review_list.ReviewListActivity
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
            ?: throw IllegalArgumentException("Required ID is not defined")

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        binding.movieId = movieId
        binding.onSeeAllListener = SeeAllClickListener({
            startActivity(Intent(this, ReviewListActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            })
        }, {
            startActivity(Intent(this, SimilarMovieListActivity::class.java).apply {
                putExtra(MOVIE_ID, movieId)
            })
        })

        viewModel.getMovieDetail(movieId)?.observe(this) {
            binding.movie = it
        }
    }

    companion object {
        const val TAG = "MovieDetailActivity"
        const val MOVIE_ID = "movie_id"
    }

    class SeeAllClickListener(
        private val onSeeAllReview: () -> Unit,
        private val onSeeAllSimilarMovies: () -> Unit
    ) {

        fun onSeeAllReviewClick() = onSeeAllReview()

        fun onSeeAllSimilarMoviesClick() = onSeeAllSimilarMovies()

    }

}