package com.rasyidcode.movieku.views

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rasyidcode.movieku.R

class MovieDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        const val movie = "movie"
        const val genres = "genres"
    }
}