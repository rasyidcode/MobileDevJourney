package com.rasyidcode.moviefinder.data.source.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TheMovieDbInterface {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") keyword: String
    ): SearchMovieResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int
    ): MovieDetailResponse

}