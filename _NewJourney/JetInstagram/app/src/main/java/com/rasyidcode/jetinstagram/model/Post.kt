package com.rasyidcode.jetinstagram.model

data class Post(
    val id: Int,
    val image: String,
    val user: User,
    val isLiked: Boolean = false,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val timestamp: Long
)
