package com.rasyidcode.movieapp.data.repository

import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.network.MovieApi
import com.rasyidcode.movieapp.data.network.MovieApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.rasyidcode.movieapp.data.domain.Movie as MovieDomain

class MovieRepository(
    private val database: MovieDatabase,
    private val movieApiService: MovieApiService
) {

    suspend fun fetchPopularMovies(page: Int) {
        withContext(Dispatchers.IO) {
            val popularMovies = movieApiService.getPopularMovies(
                page = page,
                apiKey = BuildConfig.API_KEY
            )
            val movieList: List<Movie>? = popularMovies.results?.map {
                Movie(
                    id = it?.id,
                    title = it?.title,
                    originalTitle = it?.originalTitle,
                    overview = it?.overview,
                    genreIds = it?.genreIds?.joinToString(),
                    posterPath = it?.posterPath,
                    voteAverage = it?.voteAverage,
                    listType = MovieListType.POPULAR.name
                )
            }
            movieList?.let {
                database.movieDao().insertPopularMovies(movies = it)
            }
        }
    }

    fun getPopularMovies(): Flow<List<MovieDomain>> =
        database.movieDao().getPopularMovies().map { movies ->
            movies.map { movie ->
                MovieDomain(
                    id = movie.id,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = database.genreDao().getByIds(movie.genreIds?.split(",")).map {
                        it.name ?: ""
                    },
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage
                )
            }
        }

}