package com.rasyidcode.movieapp.data.domain

import android.util.Log

data class Genre(
    val id: Int?,
    val name: String?
)

//fun List<Genre?>?.asString(genreIds: String): String? {
//    return this?.map { it?.name }?.toList()?.joinToString()
//}

fun String.asNamedGenres(genres: List<Genre?>?): String {
    val genreIds: List<Int> = this.trim().split(",").map {
        it.trim().toInt()
    }
    var genreNames = ""

    genres?.forEach {
        if (it?.id in genreIds) {
            genreNames += "${it?.name}, "
        }
    }
    genreNames = genreNames.dropLast(2)

    return genreNames
}