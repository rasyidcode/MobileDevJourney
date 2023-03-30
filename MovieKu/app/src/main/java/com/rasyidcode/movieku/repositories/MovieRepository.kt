package com.rasyidcode.movieku.repositories

import com.rasyidcode.movieku.BuildConfig
import com.rasyidcode.movieku.api.ApiConfig
import com.rasyidcode.movieku.api.ApiService
import com.rasyidcode.movieku.models.GenreResponse
import com.rasyidcode.movieku.models.MovieResponse
import retrofit2.Response

class MovieRepository {

    private val client: ApiService = ApiConfig.getApiService()

    suspend fun getPopularMovie(page: Int): Response<MovieResponse> = client.getPopularMovies(BuildConfig.API_KEY, page)

    suspend fun getGenres(): GenreResponse = client.getGenres(BuildConfig.API_KEY)
}