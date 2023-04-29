package com.rasyidcode.movieapp.data.network.genre

import com.squareup.moshi.Json

data class GenreItem(

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "id")
    val id: Int? = null
)

