package com.rasyidcode.movieapp.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasyidcode.movieapp.data.database.genre.GenreDao
import com.rasyidcode.movieapp.data.database.movie.MovieDao

abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                INSTANCE = instance

                return instance
            }
        }
    }

}