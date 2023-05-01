package com.rasyidcode.movieapp.data.repository

import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.database.genre.Genre
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
    private val movieDatabase: MovieDatabase,
    private val movieApiService: MovieApiService
) {

    suspend fun fetchPopularMovies(page: Int) {
        withContext(Dispatchers.IO) {
            val popularMovies = movieApiService.getPopularMovies(
                page = page,
                apiKey = BuildConfig.API_KEY
            )

            // Fetch genre list if not exists
            if (movieDatabase.genreDao().getCount() == 0) {
                val genres = movieApiService.getGenres(
                    apiKey = BuildConfig.API_KEY
                )
                val genreList: List<Genre>? = genres.genres?.map {
                    Genre(
                        id = it?.id,
                        name = it?.name
                    )
                }
                genreList?.let {
                    movieDatabase.genreDao().insertAll(it)
                }
            }

            val movieList: List<Movie>? = popularMovies.results?.map {
                Movie(
                    id = it?.id,
                    title = it?.title,
                    originalTitle = it?.originalTitle,
                    overview = it?.overview,
                    genreIds = it?.genreIds?.joinToString(),
                    posterPath = it?.posterPath,
                    voteAverage = it?.voteAverage,
                    releaseDate = it?.releaseDate,
                    listType = MovieListType.POPULAR.name
                )
            }
            movieList?.let {
                movieDatabase.movieDao().insertPopularMovies(movies = it)
            }
        }
    }

    fun getPopularMovies(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getPopularMovies()?.map { movies ->
            movies.map { movie ->
                val genres: List<String> = withContext(Dispatchers.IO) {
                    genreIdsToGenreList(movie.genreIds)
                }

                MovieDomain(
                    id = movie.id,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = genres.joinToString(),
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    releaseDate = movie.releaseDate
                )
            }
        }

    private fun genreIdsToGenreList(genreIds: String?): List<String> {
        return movieDatabase.genreDao().getByIds(genreIds?.split(",")).map {
            it.name ?: ""
        }
    }

    private suspend fun fetchGenres() {
        withContext(Dispatchers.IO) {
            val genres = movieApiService.getGenres(
                apiKey = BuildConfig.API_KEY
            )
            val genreList: List<Genre>? = genres.genres?.map {
                Genre(
                    id = it?.id,
                    name = it?.name
                )
            }
            genreList?.let {
                movieDatabase.genreDao().insertAll(it)
            }
        }
    }

}