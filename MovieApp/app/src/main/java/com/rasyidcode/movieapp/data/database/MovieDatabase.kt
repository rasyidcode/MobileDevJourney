package com.rasyidcode.movieapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasyidcode.movieapp.data.database.genre.GenreDao
import com.rasyidcode.movieapp.data.database.movie.MovieDao

abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

}