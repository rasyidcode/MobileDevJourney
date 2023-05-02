package com.rasyidcode.movieapp.data.repository

import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.database.genre.Genre
import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.network.MovieApiService
import com.rasyidcode.movieapp.data.network.genre.asGenreListRoom
import com.rasyidcode.movieapp.data.network.movie.asMovieLatestRoom
import com.rasyidcode.movieapp.data.network.movie.asNowPlayingRoom
import com.rasyidcode.movieapp.data.network.movie.asPopularMovieRoom
import com.rasyidcode.movieapp.data.network.movie.asTopRatedRoom
import com.rasyidcode.movieapp.data.network.movie.asUpcomingRoom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.rasyidcode.movieapp.data.domain.Movie as MovieDomain

class MovieRepository(
    private val movieDatabase: MovieDatabase,
    private val movieApiService: MovieApiService
) {

    val movieLatest: Flow<MovieDomain>? =
        movieDatabase.movieDao().getByType(listType = MovieListType.LATEST.name)?.map {
            MovieDomain(
                id = it?.id,
                title = it?.title,
                overview = it?.overview,
                genres = it?.genres,
                posterPath = it?.posterPath
            )
        }

    fun getPopularMovies(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getByListType(
            listType = MovieListType.POPULAR.name
        )?.map { movies ->
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

    fun getNowPlaying(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getByListType(
            listType = MovieListType.NOW_PLAYING.name
        )?.map { movies ->
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

    fun getTopRated(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getByListType(
            listType = MovieListType.TOP_RATED.name
        )?.map { movies ->
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

    fun getUpcoming(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getByListType(
            listType = MovieListType.UPCOMING.name
        )?.map { movies ->
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
                val genreList: List<Genre>? = genres.genres?.asGenreListRoom()
                genreList?.let {
                    movieDatabase.genreDao().insertAll(it)
                }
            }

            val movieList: List<Movie>? = popularMovies.results?.asPopularMovieRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchNowPlaying(page: Int) {
        withContext(Dispatchers.IO) {
            val nowPlaying = movieApiService.getNowPlayingMovies(
                page = page,
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = nowPlaying.results?.asNowPlayingRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchTopRated(page: Int) {
        withContext(Dispatchers.IO) {
            val topRated = movieApiService.getTopRatedMovies(
                page = page,
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = topRated.results?.asTopRatedRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchUpcoming(page: Int) {
        withContext(Dispatchers.IO) {
            val upcoming = movieApiService.getUpComingMovies(
                page = page,
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = upcoming.results?.asUpcomingRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchLatestMovie() {
        withContext(Dispatchers.IO) {
            val latest = movieApiService.getLatestMovies(
                apiKey = BuildConfig.API_KEY
            )

            val movieLatest: Movie = latest.asMovieLatestRoom()
            movieDatabase.movieDao().insert(movieLatest)
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