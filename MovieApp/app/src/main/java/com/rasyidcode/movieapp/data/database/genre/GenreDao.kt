package com.rasyidcode.movieapp.data.database.genre

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("SELECT * FROM genre ORDER BY id ASC")
    fun getAll(): Flow<List<Genre>>

    @Query("SELECT * FROM genre WHERE id IN (:ids)")
    fun getByIds(ids: List<String>?): List<Genre>

    @Query("SELECT COUNT(id) FROM genre")
    fun getCount(): Int

    @Insert
    suspend fun insertAll(genres: List<Genre>)

}