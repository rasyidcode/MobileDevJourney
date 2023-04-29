package com.rasyidcode.movieapp.data.database.review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "review")
data class Review(
    @PrimaryKey(autoGenerate = false)
    val id: String?,
    @ColumnInfo(name = "username")
    val username: String?,
    @ColumnInfo(name = "avatar_path")
    val avatarPath: String?,
    @ColumnInfo(name = "author")
    val author: String?,
    @ColumnInfo(name = "rating")
    val rating: Int?,
    @ColumnInfo(name = "content")
    val content: String?,
    @ColumnInfo(name = "url")
    val url: String?,
    @ColumnInfo(name = "created_at")
    val createdAt: String?,
    @ColumnInfo(name = "updated_at")
    val updatedAt: String?,
)