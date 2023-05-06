package com.rasyidcode.movieapp.ui.similar_movie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.movieapp.databinding.ActivitySimilarMovieListBinding

class SimilarMovieListActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySimilarMovieListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySimilarMovieListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}