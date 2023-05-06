package com.rasyidcode.movieapp.ui.movie_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.movieapp.MovieApplication
import com.rasyidcode.movieapp.databinding.ActivityMovieDetailBinding

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

        binding.viewModel = viewModel
        binding.lifecycleOwner = this
    }

}