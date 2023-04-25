package com.rasyidcode.forageapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rasyidcode.forageapp.model.Forageable
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for database interaction
 */
@Dao
interface ForageableDao {

    // implement a method to retrieve all Forageables from the database
    @Query("SELECT * FROM forageable")
    fun getForageables(): Flow<List<Forageable>>

    // implement a method to retrieve a Foregable from the database by id
    @Query("SELECT * FROM forageable WHERE id = :id")
    fun getForageable(id: Long): Flow<Forageable>

    // implement a method to insert a Foregeable into the database
    //  (use OnConflictStrategy.REPLACE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forageable: Forageable)

    // implement a method to update a Foregeable that is already in the database
    @Update
    suspend fun update(forageable: Forageable)

    // implement a method to delete a Forageable from the database
    suspend fun delete(forageable: Forageable)
}