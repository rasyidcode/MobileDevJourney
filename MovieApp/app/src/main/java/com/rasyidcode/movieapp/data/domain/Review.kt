package com.rasyidcode.movieapp.data.domain

data class Review(
    val id: Int?,
    val reviewId: String?,
    val username: String?,
    val avatarPath: String?,
    val author: String?,
    val rating: Float?,
    val content: String?,
    val url: String?,
    val createdAt: String?,
    val updatedAt: String?,
    val movieId: Int?
)
