package com.rasyidcode.jetinstagram.model

import android.net.Uri

data class Reel(
    val id: Int,
    private val video: String,
    val user: User,
    val isLiked: Boolean =false,
    val likesCount: Int,
    val commentsCount: Int
) {
    val videoURL: Uri
        get() = Uri.parse("asset:///${video}")
}
