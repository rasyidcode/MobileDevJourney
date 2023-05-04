package com.rasyidcode.movieapp

import android.app.Application
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.network.MovieApi
import com.rasyidcode.movieapp.data.network.MovieApiService
import com.rasyidcode.movieapp.data.repository.MovieRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MovieApplication : Application() {

    private val movieDatabase: MovieDatabase by lazy { MovieDatabase.getDatabase(this) }

    private val movieApiService: MovieApiService by lazy { MovieApi.getService() }

    val movieRepository: MovieRepository by lazy {
        MovieRepository(movieDatabase, movieApiService)
    }

}