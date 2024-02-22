package com.ipritdev.androidtesting.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ipritdev.androidtesting.data.Task

/**
 * Data Access Object for the tasks table
 */
@Dao
interface TaskDao {

    /**
     * Observes list of tasks
     *
     * @return all tasks.
     */
    @Query("SELECT * FROM tasks")
    fun observeTasks(): LiveData<List<Task>>

    /**
     * Observes a single task
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE entry_id = :taskId")
    fun observeTaskById(taskId: String): LiveData<Task>

    /**
     * Select all tasks from the tasks table
     *
     * @return all tasks
     */
    @Query("SELECT * FROM tasks")
    suspend fun getTasks(): List<Task>

    /**
     * Select a task by id
     *
     * @param taskId the task id
     * @return the task with taskId
     */
    @Query("SELECT * FROM tasks WHERE entry_id = :taskId")
    suspend fun getTaskById(taskId: String): Task?

    /**
     * Insert a task in the database. If the task already exists, replace it.
     *
     * @param task the task to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task)

    /**
     * Update a task
     *
     * @param task task to be updated
     * @return the number of tasks updated. This should be always 1
     */
    @Update
    suspend fun updateTask(task: Task): Int

    /**
     * Update the complete status of a task
     *
     * @param taskId id of the task
     * @param completed status to be updated
     */
    @Query("UPDATE tasks SET completed = :completed WHERE entry_id = :taskId")
    suspend fun updateCompleted(taskId: String, completed: Boolean)

    /**
     * Delete a task by id
     *
     * @return the number of tasks deleted. This should be always 1
     */
    @Query("DELETE FROM tasks WHERE entry_id = :taskId")
    suspend fun deleteByTaskId(taskId: String)

    /**
     * Delete all tasks
     */
    @Query("DELETE FROM tasks")
    suspend fun deleteTasks()

    /**
     * Delete all completed tasks
     *
     * @return the number of tasks deleted
     */
    @Query("DELETE FROM tasks WHERE completed = 1")
    suspend fun deleteCompletedTasks(): Int

}