package com.rasyidcode.moviefinder.data.repository

import android.util.Log
import com.rasyidcode.moviefinder.data.ResultData
import com.rasyidcode.moviefinder.data.source.SearchMovieDataSource
import com.rasyidcode.moviefinder.data.source.network.SearchMovieResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchMovieRepositoryImpl @Inject constructor(
    private val searchMovieDataSource: SearchMovieDataSource
) : SearchMovieRepository {

    override fun searchMovie(keyword: String): Flow<ResultData<SearchMovieResponse>> = flow {
        emit(ResultData.Loading)

        try {
            val result = searchMovieDataSource.searchMovie(keyword)

            Log.d("SearchMovieRepository", result.results?.get(0)?.title.toString())

            emit(ResultData.Success(result))
        } catch (t: Throwable) {
            Log.e("SearchMovieRepository", t.message.toString())

            emit(ResultData.Failed(errorMessage = t.message))
        }
    }

}