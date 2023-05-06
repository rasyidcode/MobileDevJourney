package com.rasyidcode.movieapp.data.database.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review ORDER BY id ASC")
    fun getAll(): Flow<List<Review>>

    @Query("SELECT * FROM review WHERE movie_id = :movieId ORDER BY id")
    fun getByMovieId(movieId: Int): Flow<List<Review>>

    @Insert
    suspend fun insertAll(reviews: List<Review>?)

}