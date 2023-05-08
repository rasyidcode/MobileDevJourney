package com.rasyidcode.movieapp.data.network.movie

import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieListType
import com.rasyidcode.movieapp.data.network.genre.GenreItem
import com.squareup.moshi.Json

data class MovieDetailResponse(
    @Json(name = "id")
    val id: Int? = null,
    @Json(name = "title")
    val title: String? = null,
    @Json(name = "overview")
    val overview: String? = null,
    @Json(name = "original_title")
    val originalTitle: String? = null,
    @Json(name = "original_language")
    val originalLanguage: String? = null,
    @Json(name = "poster_path")
    val posterPath: String? = null,
    @Json(name = "revenue")
    val revenue: Int? = null,
    @Json(name = "genres")
    val genres: List<GenreItem?>? = null,
    @Json(name = "popularity")
    val popularity: Double? = null,
    @Json(name = "tagline")
    val tagline: String? = null,
    @Json(name = "vote_count")
    val voteCount: Int? = null,
    @Json(name = "budget")
    val budget: Int? = null,
    @Json(name = "runtime")
    val runtime: Int? = null,
    @Json(name = "release_date")
    val releaseDate: String? = null,
    @Json(name = "vote_average")
    val voteAverage: Float? = null,
    @Json(name = "status")
    val status: String? = null
)

fun MovieDetailResponse?.asMovieLatestRoom(): Movie {
    return Movie(
        movieId = this?.id,
        title = this?.title,
        overview = this?.overview,
        posterPath = this?.posterPath,
        genres = this?.genres?.joinToString(),
        listType = MovieListType.LATEST.name
    )
}

fun MovieDetailResponse?.asMovieDetailRoom(listType: String, id: Int?): Movie {
    return Movie(
        id = id,
        movieId = this?.id,
        title = this?.title,
        overview = this?.overview,
        originalTitle = this?.originalTitle,
        originalLanguage = this?.originalLanguage,
        posterPath = this?.posterPath,
        revenue = this?.revenue,
        genreIds = this?.genres?.map {
            it?.id
        }?.joinToString(","),
        popularity = this?.popularity,
        tagline = this?.tagline,
        voteCount = this?.voteCount,
        budget = this?.budget,
        runtime = this?.runtime,
        releaseDate = this?.releaseDate,
        voteAverage = this?.voteAverage,
        status = this?.status,
        listType = listType
    )
}