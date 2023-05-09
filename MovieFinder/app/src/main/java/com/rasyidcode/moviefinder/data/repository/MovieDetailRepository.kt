package com.rasyidcode.moviefinder.data.repository

import com.rasyidcode.moviefinder.data.ResultData
import com.rasyidcode.moviefinder.data.source.network.MovieDetailResponse
import kotlinx.coroutines.flow.Flow

interface MovieDetailRepository {

    fun getDetails(movieId: Int): Flow<ResultData<MovieDetailResponse>>

}