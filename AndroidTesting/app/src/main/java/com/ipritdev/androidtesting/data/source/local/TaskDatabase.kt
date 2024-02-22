package com.ipritdev.androidtesting.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ipritdev.androidtesting.data.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

}