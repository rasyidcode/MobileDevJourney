package com.rasyidcode.movieapp.data.database.review

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ReviewDao {

    @Query("SELECT * FROM review")
    fun getAll(): Flow<List<Review>>

    @Query("SELECT * FROM review WHERE movie_id = :movieId")
    fun getByMovieId(movieId: Int): Flow<List<Review>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(reviews: List<Review>?)

}