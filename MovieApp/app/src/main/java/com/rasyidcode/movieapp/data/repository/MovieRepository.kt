package com.rasyidcode.movieapp.data.repository

import android.util.Log
import com.rasyidcode.movieapp.BuildConfig
import com.rasyidcode.movieapp.data.database.MovieDatabase
import com.rasyidcode.movieapp.data.database.genre.Genre
import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
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
import com.rasyidcode.movieapp.data.domain.Genre as GenreDomain
import com.rasyidcode.movieapp.data.domain.Movie as MovieDomain
import com.rasyidcode.movieapp.data.domain.Review as ReviewDomain
import com.rasyidcode.movieapp.data.database.review.Review

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
                genres = it?.genreIds,
                posterPath = it?.posterPath
            )
        }

    val genres: Flow<List<GenreDomain?>?>? = movieDatabase.genreDao().getAll()?.map {
        it?.map { genre ->
            GenreDomain(
                id = genre?.id,
                name = genre?.name
            )
        }
    }

    fun getPopularMovies(): Flow<List<MovieDomain>>? = movieDatabase.movieDao().getByListType(
        listType = MovieListType.POPULAR.name
    )?.map { movies ->
        movies.map { movie ->
            MovieDomain(
                id = movie.id,
                movieId = movie.movieId,
                title = movie.title,
                originalTitle = movie.originalTitle,
                overview = movie.overview,
                genres = movie.genreIds,
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
                MovieDomain(
                    id = movie.id,
                    movieId = movie.movieId,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = movie.genreIds,
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
                MovieDomain(
                    id = movie.id,
                    movieId = movie.movieId,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = movie.genreIds,
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
                MovieDomain(
                    id = movie.id,
                    movieId = movie.movieId,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = movie.genreIds,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    releaseDate = movie.releaseDate
                )
            }
        }

    fun getMovieDetail(id: Int?, movieId: Int?, movieListType: String?): Flow<MovieDomain?>? =
        movieDatabase.movieDao().getById(
            movieId = movieId,
            id = id,
            listType = movieListType
        )?.map {
            MovieDomain(
                movieId = it?.id,
                title = it?.title,
                originalTitle = it?.originalTitle,
                overview = it?.overview,
                originalLanguage = it?.originalLanguage,
                genres = it?.genreIds,
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

    fun getReviews(movieId: Int): Flow<List<ReviewDomain>>? =
        movieDatabase.reviewDao().getByMovieId(movieId).map { reviews ->

            Log.d(TAG, "getReviews(): ${reviews.size}")

            reviews.map { review ->
                ReviewDomain(
                    id = review.id,
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
                MovieDomain(
                    id = movie.id,
                    title = movie.title,
                    originalTitle = movie.originalTitle,
                    overview = movie.overview,
                    genres = movie.genreIds,
                    posterPath = movie.posterPath,
                    voteAverage = movie.voteAverage,
                    releaseDate = movie.releaseDate
                )
            }
        }

    suspend fun fetchNetworkGenres() = withContext(Dispatchers.IO) {
        val genres = movieApiService.getGenres(
            apiKey = BuildConfig.API_KEY
        )
        val genreList: List<Genre>? = genres.genres?.asGenreListRoom()
        genreList?.let {
            movieDatabase.genreDao().insertAll(it)
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

    suspend fun fetchTopRated(page: Int, withGenres: List<String>? = null) {
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

    suspend fun fetchUpcoming(page: Int, withGenres: List<String>? = null) {
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

    suspend fun clearMovieData() = withContext(Dispatchers.IO) {
        movieDatabase.movieDao().deleteAll()
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

    suspend fun fetchMovieDetail(id: Int, movieId: Int, movieListType: String) {
        withContext(Dispatchers.IO) {
            val movieDetail = movieApiService.getMovieDetail(
                id = movieId,
                apiKey = BuildConfig.API_KEY
            )

            val movieDetailRoom = movieDetail.asMovieDetailRoom(
                listType = movieListType,
                id = id
            )

            movieDatabase.movieDao().update(
                movie = movieDetailRoom
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
            val reviewList: List<Review>? = reviews.results?.asReviewRoom(
                movieId = movieId
            )

            Log.d(TAG, "review: $reviewList")

            movieDatabase.reviewDao().insertAll(
                reviews = reviewList
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

    companion object {
        const val TAG = "MovieRepository"
    }

}