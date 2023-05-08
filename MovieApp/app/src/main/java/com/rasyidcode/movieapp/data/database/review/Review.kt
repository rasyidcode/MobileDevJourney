package com.rasyidcode.movieapp.data.database.review

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val username: String?,
    @ColumnInfo(name = "avatar_path")
    val avatarPath: String?,
    val author: String?,
    val rating: Float?,
    val content: String?,
    val url: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String?,
    @ColumnInfo(name = "movie_id")
    val movieId: Int?
)