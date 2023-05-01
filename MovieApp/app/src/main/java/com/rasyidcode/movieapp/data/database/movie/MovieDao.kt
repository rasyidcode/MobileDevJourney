package com.rasyidcode.movieapp.data.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE list_type = :listType ORDER BY id ASC")
    fun getByListType(listType: String?): Flow<List<Movie>>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)
}