package com.rasyidcode.moviefinder.data.source.network

import com.rasyidcode.moviefinder.data.source.SearchMovieDataSource
import javax.inject.Inject

class SearchMovieDataSourceImpl @Inject constructor(
    private val apiService: TheMovieDbInterface
) : SearchMovieDataSource {

    override suspend fun searchMovie(keyword: String): SearchMovieResponse {
        return apiService.searchMovies(keyword)
    }

}