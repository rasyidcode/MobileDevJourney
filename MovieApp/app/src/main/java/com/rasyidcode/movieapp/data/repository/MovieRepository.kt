package com.rasyidcode.movieapp.data.repository

import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.database.genre.Genre
import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.domain.Review
import com.rasyidcode.movieapp.data.network.MovieApiService
import com.rasyidcode.movieapp.data.network.genre.asGenreListRoom
import com.rasyidcode.movieapp.data.network.movie.asMovieDetailRoom
import com.rasyidcode.movieapp.data.network.movie.asMovieLatestRoom
import com.rasyidcode.movieapp.data.network.movie.asNowPlayingRoom
import com.rasyidcode.movieapp.data.network.movie.asPopularMovieRoom
import com.rasyidcode.movieapp.data.network.movie.asSimilarMoviesRoom
import com.rasyidcode.movieapp.data.network.movie.asTopRatedRoom
import com.rasyidcode.movieapp.data.network.movie.asUpcomingRoom
import com.rasyidcode.movieapp.data.network.review.asReviewRoom
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
            val genres: List<String>? = withContext(Dispatchers.IO) {
                it?.genreIds?.let { genres ->
                    genreIdsToGenreList(genres)
                }
            }
            MovieDomain(
                id = it?.id,
                title = it?.title,
                overview = it?.overview,
                genres = genres?.joinToString(),
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

    fun getMovieDetail(movieId: Int?): Flow<MovieDomain>? = movieDatabase.movieDao().getById(
        movieId = movieId
    )?.map {
        MovieDomain(
            movieId = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            originalLanguage = it?.originalLanguage,
            genres = it?.genres,
            posterPath = it?.posterPath,
            releaseDate = it?.releaseDate,
            voteAverage = it?.voteAverage,
            voteCount = it?.voteCount,
            revenue = it?.revenue,
            popularity = it?.popularity,
            tagline = it?.tagline,
            budget = it?.budget,
            runtime = it?.runtime,
            status = it?.status
        )
    }

    fun getReviews(movieId: Int): Flow<List<Review>>? =
        movieDatabase.reviewDao().getByMovieId(movieId).map { reviews ->
            reviews.map { review ->
                Review(
                    id = review.id,
                    reviewId = review.reviewId,
                    username = review.username,
                    avatarPath = review.avatarPath,
                    author = review.author,
                    rating = review.rating,
                    content = review.content,
                    url = review.url,
                    createdAt = review.createdAt,
                    updatedAt = review.updatedAt,
                    movieId = review.movieId
                )
            }
        }

    fun getSimilarMovies(): Flow<List<MovieDomain>>? =
        movieDatabase.movieDao().getByListType(
            listType = MovieListType.SIMILAR.name
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

    suspend fun fetchNetworkGenres() {
        withContext(Dispatchers.IO) {
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
        }
    }

    suspend fun fetchPopularMovies(page: Int, withGenres: List<String>? = null) {
        withContext(Dispatchers.IO) {
            val popularMovies = movieApiService.getPopularMovies(
                page = page,
                withGenres = withGenres?.joinToString(),
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = popularMovies.results?.asPopularMovieRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchNowPlaying(page: Int, withGenres: List<String>? = null) {
        withContext(Dispatchers.IO) {
            val nowPlaying = movieApiService.getNowPlayingMovies(
                page = page,
                withGenres = withGenres?.joinToString(),
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = nowPlaying.results?.asNowPlayingRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchTopRated(page: Int, withGenres: List<String>?) {
        withContext(Dispatchers.IO) {
            val topRated = movieApiService.getTopRatedMovies(
                page = page,
                withGenres = withGenres?.joinToString(),
                apiKey = BuildConfig.API_KEY
            )

            val movieList: List<Movie>? = topRated.results?.asTopRatedRoom()
            movieList?.let {
                movieDatabase.movieDao().insertAll(movies = it)
            }
        }
    }

    suspend fun fetchUpcoming(page: Int, withGenres: List<String>?) {
        withContext(Dispatchers.IO) {
            val upcoming = movieApiService.getUpComingMovies(
                page = page,
                withGenres = withGenres?.joinToString(),
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

    suspend fun clearData() {
        withContext(Dispatchers.IO) {
            movieDatabase.clearAllTables()
        }
    }

    suspend fun clearList(
        listType: MovieListType
    ) {
        withContext(Dispatchers.IO) {
            movieDatabase.movieDao().deleteAllByListType(
                listType = listType.name
            )
        }
    }

    suspend fun fetchMovieDetail(id: Int, movieListType: MovieListType) {
        withContext(Dispatchers.IO) {
            val movieDetail = movieApiService.getMovieDetail(
                id = id,
                apiKey = BuildConfig.API_KEY
            )
            movieDatabase.movieDao().update(
                movie = movieDetail.asMovieDetailRoom(
                    listType = movieListType
                )
            )
        }
    }

    suspend fun fetchReviews(movieId: Int, page: Int) {
        withContext(Dispatchers.IO) {
            val reviews = movieApiService.getReviews(
                movieId = movieId,
                page = page,
                apiKey = BuildConfig.API_KEY
            )
            movieDatabase.reviewDao().insertAll(
                reviews = reviews.results?.asReviewRoom(
                    movieId = movieId
                )
            )
        }
    }

    suspend fun fetchSimilarMovies(movieId: Int, page: Int) {
        withContext(Dispatchers.IO) {
            val reviews = movieApiService.getSimilarMovies(
                movieId = movieId,
                page = page,
                apiKey = BuildConfig.API_KEY
            )

            reviews.results?.asSimilarMoviesRoom()?.let {
                movieDatabase.movieDao().insertAll(
                    movies = it
                )
            }
        }
    }

    private fun genreIdsToGenreList(genreIds: String?): List<String> {
        val genreList = genreIds?.split(",")
        var res: List<String> = emptyList()
        genreList?.let {
            res = movieDatabase.genreDao().getByIds(genreList).map {
                it.name ?: ""
            }
        }

        return res
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