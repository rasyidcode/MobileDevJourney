package com.rasyidcode.movieapp.data.domain

data class Movie(
    val id: Int? = null,
    val movieId: Int? = null,
    val title: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val originalLanguage: String? = null,
    val genres: String? = null,
    val posterPath: String? = null,
    val releaseDate: String? = null,
    val voteAverage: Float? = null,
    val voteCount: Int? = null,
    val revenue: Int? = null,
    val popularity: Double? = null,
    val tagline: String? = null,
    val budget: Int? = null,
    val runtime: Int? = null,
    val status: String? = null
)
