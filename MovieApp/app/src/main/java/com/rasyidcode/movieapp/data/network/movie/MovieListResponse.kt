package com.rasyidcode.movieapp.data.network.movie

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
    val voteAverage: Double? = null
)
