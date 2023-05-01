package com.rasyidcode.movieapp.data.network.genre

import com.rasyidcode.movieapp.data.database.genre.Genre
import com.squareup.moshi.Json

data class GenreItem(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: Int? = null
)

fun List<GenreItem?>?.asGenreListRoom(): List<Genre>? {
    return this?.map {
        Genre(
            id = it?.id,
            name = it?.name
        )
    }
}
