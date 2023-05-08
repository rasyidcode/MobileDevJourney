package com.rasyidcode.movieapp.data.database.movie

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rasyidcode.movieapp.data.network.genre.GenreItem
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * FROM movie WHERE list_type = :listType ORDER BY id ASC")
    fun getByListType(listType: String?): Flow<List<Movie>>?

    @Query("SELECT * FROM movie WHERE list_type = :listType ORDER BY id DESC")
    fun getByType(listType: String?): Flow<Movie?>?

    @Query("SELECT * FROM movie WHERE id = :id AND movie_id = :movieId AND list_type = :listType")
    fun getById(id: Int?, movieId: Int?, listType: String?): Flow<Movie?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    suspend fun update(movie: Movie)

    @Query("DELETE FROM movie WHERE list_type = :listType")
    suspend fun deleteAllByListType(listType: String)

    @Query("DELETE FROM movie")
    suspend fun deleteAll()
}