package com.rasyidcode.movieku.views

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.rasyidcode.movieku.BuildConfig
import com.rasyidcode.movieku.databinding.ActivityMovieDetailBinding
import com.rasyidcode.movieku.models.Movie

class MovieDetail : AppCompatActivity() {

    private var _binding: ActivityMovieDetailBinding? = null
    private val binding get() = _binding

    private inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        Build.VERSION.SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        intent.parcelable<Movie>(movie)?.let { movie ->
            intent.getStringExtra(genres)?.let { genres ->
                binding?.apply {
                    Glide.with(this@MovieDetail).load("${BuildConfig.PHOTO_BASE_URL}${movie.posterPath}").into(posterDetail)
                    titleDetail.text = movie.title
                    releaseDateDetail.text = movie.releaseDate
                    ratingText.text = movie.voteAverage.toString()
                    ratingBar.rating = movie.voteAverage?.div(2) ?: 0f
                    genreDetail.text = genres.dropLast(2)
                    overview.text = movie.overview
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        const val movie = "movie"
        const val genres = "genres"
    }
}