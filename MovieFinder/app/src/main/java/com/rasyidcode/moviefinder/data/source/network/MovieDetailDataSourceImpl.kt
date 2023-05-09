package com.rasyidcode.moviefinder.data.source.network

import com.rasyidcode.moviefinder.data.source.MovieDetailDataSource
import javax.inject.Inject

class MovieDetailDataSourceImpl @Inject constructor(
    private val apiService: TheMovieDbInterface
) : MovieDetailDataSource {

    override suspend fun getMovieDetails(movieId: Int): MovieDetailResponse {
        return apiService.getMovieDetail(movieId)
    }

}