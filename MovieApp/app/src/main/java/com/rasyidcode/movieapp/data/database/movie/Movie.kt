package com.rasyidcode.movieapp.data.database.movie

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rasyidcode.movieapp.data.domain.Movie as MovieDomain

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    @ColumnInfo(name = "movie_id")
    val movieId: Int? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,
    @ColumnInfo(name = "overview")
    val overview: String? = null,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,
    @ColumnInfo(name = "genre_ids")
    val genreIds: String? = null,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Float? = null,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null,
    @ColumnInfo(name = "revenue")
    val revenue: Int? = null,
    @ColumnInfo(name = "genres")
    val genres: String? = null,
    @ColumnInfo(name = "popularity")
    val popularity: Double? = null,
    @ColumnInfo(name = "tagline")
    val tagline: String? = null,
    @ColumnInfo(name = "budget")
    val budget: Int? = null,
    @ColumnInfo(name = "runtime")
    val runtime: Int? = null,
    @ColumnInfo(name = "status")
    val status: String? = null,
    @ColumnInfo(name = "list_type")
    val listType: String?
)

enum class MovieListType {
    POPULAR,
    NOW_PLAYING,
    TOP_RATED,
    UPCOMING,
    LATEST,
    SIMILAR
}

fun Movie.asMovieLatestDomain(): MovieDomain {
    return MovieDomain(
        id = this.id,
        title = this.title,
        overview = this.overview,
        genres = this.genres,
        posterPath = this.posterPath
    )
}