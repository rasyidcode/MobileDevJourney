package com.rasyidcode.movieapp.data.network.movie

import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.squareup.moshi.Json

data class MovieListResponse(
    @Json(name = "page")
    val page: Int? = null,
    @Json(name = "total_pages")
    val totalPages: Int? = null,
    @Json(name = "results")
    val results: List<MovieItem?>? = null,
    @Json(name = "total_results")
    val totalResults: Int? = null
)

data class MovieItem(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "original_title")
    val originalTitle: String? = null,
    @Json(name = "overview")
    val overview: String? = null,
    @Json(name = "genre_ids")
    val genreIds: List<Int?>? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "vote_average")
    val voteAverage: Float? = null,
    @Json(name = "release_date")
    val releaseDate: String? = null,
)

fun List<MovieItem?>?.asPopularMovieRoom(): List<Movie>? {
    return this?.map {
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
}

fun List<MovieItem?>?.asNowPlayingRoom(): List<Movie>? {
    return this?.map {
        Movie(
            id = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            genreIds = it?.genreIds?.joinToString(),
            posterPath = it?.posterPath,
            voteAverage = it?.voteAverage,
            releaseDate = it?.releaseDate,
            listType = MovieListType.NOW_PLAYING.name
        )
    }
}

fun List<MovieItem?>?.asTopRatedRoom(): List<Movie>? {
    return this?.map {
        Movie(
            id = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            genreIds = it?.genreIds?.joinToString(),
            posterPath = it?.posterPath,
            voteAverage = it?.voteAverage,
            releaseDate = it?.releaseDate,
            listType = MovieListType.TOP_RATED.name
        )
    }
}

fun List<MovieItem?>?.asUpcomingRoom(): List<Movie>? {
    return this?.map {
        Movie(
            id = it?.id,
            title = it?.title,
            originalTitle = it?.originalTitle,
            overview = it?.overview,
            genreIds = it?.genreIds?.joinToString(),
            posterPath = it?.posterPath,
            voteAverage = it?.voteAverage,
            releaseDate = it?.releaseDate,
            listType = MovieListType.UPCOMING.name
        )
    }
}