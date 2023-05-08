package com.rasyidcode.movieapp.data.database.genre

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre")
    fun getAll(): Flow<List<Genre?>?>?

    @Query("SELECT * FROM genre")
    fun getGenres(): List<Genre?>?

    @Query("SELECT * FROM genre WHERE id IN (:ids)")
    fun getByIds(ids: List<String>?): List<Genre>

    @Query("SELECT COUNT(id) FROM genre")
    fun getCount(): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(genres: List<Genre>)

    @Query("DELETE FROM genre")
    suspend fun deleteAll()
}