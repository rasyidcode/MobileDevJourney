package com.rasyidcode.moviefinder.data.source

import com.rasyidcode.moviefinder.data.source.network.SearchMovieResponse

interface SearchMovieDataSource {

    suspend fun searchMovie(keyword: String): SearchMovieResponse

}