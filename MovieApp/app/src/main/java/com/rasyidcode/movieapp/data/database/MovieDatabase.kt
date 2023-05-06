package com.rasyidcode.movieapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rasyidcode.movieapp.data.database.genre.Genre
import com.rasyidcode.movieapp.data.database.genre.GenreDao
import com.rasyidcode.movieapp.data.database.movie.Movie
import com.rasyidcode.movieapp.data.database.movie.MovieDao
import com.rasyidcode.movieapp.data.database.review.Review
import com.rasyidcode.movieapp.data.database.review.ReviewDao

@Database(entities = [Movie::class, Genre::class, Review::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    abstract fun genreDao(): GenreDao

    abstract fun reviewDao(): ReviewDao

    companion object {
        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getDatabase(context: Context): MovieDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MovieDatabase::class.java,
                    "app_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance

                return instance
            }
        }
    }

}