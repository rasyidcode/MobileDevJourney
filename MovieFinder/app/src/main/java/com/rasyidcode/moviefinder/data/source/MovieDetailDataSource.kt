package com.rasyidcode.moviefinder.data.source

import com.rasyidcode.moviefinder.data.source.network.MovieDetailResponse

interface MovieDetailDataSource {

    suspend fun getMovieDetails(movieId: Int): MovieDetailResponse

}