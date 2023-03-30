package com.rasyidcode.movieku.api

import com.rasyidcode.movieku.models.GenreResponse
import com.rasyidcode.movieku.models.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") key:String?,
        @Query("page") page: Int?
    ) : Response<MovieResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") key:String?,
    ) : GenreResponse
}