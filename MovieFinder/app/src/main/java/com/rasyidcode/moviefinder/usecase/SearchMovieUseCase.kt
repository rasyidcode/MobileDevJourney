package com.rasyidcode.moviefinder.usecase

import com.rasyidcode.moviefinder.data.ResultData
import com.rasyidcode.moviefinder.data.repository.SearchMovieRepository
import com.rasyidcode.moviefinder.data.source.network.SearchMovieResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(
    private val repository: SearchMovieRepository
) {

    operator fun invoke(keyword: String): Flow<ResultData<SearchMovieResponse>> =
        repository.searchMovie(keyword)

}