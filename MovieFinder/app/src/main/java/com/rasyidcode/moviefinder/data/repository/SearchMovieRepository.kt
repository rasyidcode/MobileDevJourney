package com.rasyidcode.moviefinder.data.repository

import com.rasyidcode.moviefinder.data.ResultData
import com.rasyidcode.moviefinder.data.source.network.SearchMovieResponse
import kotlinx.coroutines.flow.Flow

interface SearchMovieRepository {

    fun searchMovie(keyword: String): Flow<ResultData<SearchMovieResponse>>

}