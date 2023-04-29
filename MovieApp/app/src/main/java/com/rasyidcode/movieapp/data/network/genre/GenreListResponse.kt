package com.rasyidcode.movieapp.data.network.genre

import com.squareup.moshi.Json

data class GenreListResponse(

    @Json(name = "genres")
    val genres: List<GenreItem?>? = null
)