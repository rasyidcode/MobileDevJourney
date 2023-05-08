package com.rasyidcode.todoapp.data.source.local

import androidx.room.Database
import com.rasyidcode.todoapp.data.Task

/**
 * The Room Database that contains the Task table
 *
 * Note that exportSchema should be true in production databases
 */
@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TodoDatabase {

    abstract fun taskDao(): TasksDao

}