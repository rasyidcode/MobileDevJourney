package com.rasyidcode.movieapp.data.database.genre

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "genre")
data class Genre(
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?
)