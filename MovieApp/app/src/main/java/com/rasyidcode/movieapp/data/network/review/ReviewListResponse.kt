package com.rasyidcode.movieapp.data.network.review

import com.rasyidcode.movieapp.data.database.review.Review
import com.squareup.moshi.Json

data class ReviewListResponse(

    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "page")
    val page: Int? = null,

    @Json(name = "total_pages")
    val totalPages: Int? = null,

    @Json(name = "results")
    val results: List<ReviewItem?>? = null,

    @Json(name = "total_results")
    val totalResults: Int? = null
)

data class ReviewItem(
    @Json(name = "author_details")
    val authorDetails: AuthorDetail? = null,
    @Json(name = "updated_at")
    val updatedAt: String? = null,
    @Json(name = "author")
    val author: String? = null,
    @Json(name = "created_at")
    val createdAt: String? = null,
    @Json(name = "id")
    val id: String? = null,
    @Json(name = "content")
    val content: String? = null,
    @Json(name = "url")
    val url: String? = null
)

data class AuthorDetail(

    @Json(name = "avatar_path")
    val avatarPath: String? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "rating")
    val rating: Float? = null,

    @Json(name = "username")
    val username: String? = null
)

fun List<ReviewItem?>?.asReviewRoom(movieId: Int): List<Review>? {
    return this?.map {
        Review(
            id = null,
            reviewId = it?.id,
            username = it?.authorDetails?.username,
            avatarPath = it?.authorDetails?.avatarPath,
            author = it?.author,
            rating = it?.authorDetails?.rating,
            url = it?.url,
            createdAt = it?.createdAt,
            updatedAt = it?.updatedAt,
            content = it?.content,
            movieId = movieId
        )
    }
}