package com.rasyidcode.movieapp.data.network

import com.rasyidcode.movieapp.data.network.genre.GenreListResponse
import com.rasyidcode.movieapp.data.network.movie.MovieDetailResponse
import com.rasyidcode.movieapp.data.network.movie.MovieListResponse
import com.rasyidcode.movieapp.data.network.review.ReviewListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApiService {

    @GET("movie/latest")
    suspend fun getLatestMovies(
        @Query("api_key") apiKey: String
    ): MovieDetailResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("with_genres") withGenres: String? = null,
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
        @Query("api_key") apiKey: String
    ): MovieDetailResponse

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("page") page: Int,
        @Query("query") keyword: String,
        @Query("year") year: String,
        @Query("include_adult") adult: Boolean,
        @Query("api_key") apiKey: String
    )

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): MovieListResponse

    @GET("movie/{movie_id}/reviews")
    suspend fun getReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): ReviewListResponse

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): GenreListResponse
}