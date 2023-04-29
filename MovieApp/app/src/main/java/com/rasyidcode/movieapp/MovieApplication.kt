package com.rasyidcode.movieapp

import android.app.Application
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.network.MovieApi
import com.rasyidcode.movieapp.data.network.MovieApiService

class MovieApplication : Application() {

    val database: MovieDatabase by lazy { MovieDatabase.getDatabase(this) }

    val movieApiService: MovieApiService by lazy { MovieApi.getService() }

}