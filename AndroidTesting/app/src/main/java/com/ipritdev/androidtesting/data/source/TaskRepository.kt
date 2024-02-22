package com.ipritdev.androidtesting.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.ipritdev.androidtesting.data.Result
import com.ipritdev.androidtesting.data.Task
import com.ipritdev.androidtesting.data.source.local.TaskDatabase
import com.ipritdev.androidtesting.data.source.local.TaskLocalDataSource
import com.ipritdev.androidtesting.data.source.remote.TaskRemoteDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Concrete implementation to load tasks from the data sources into cache
 */
class TaskRepository private constructor(
    application: Application
) {

    private val taskRemoteDataSource: TaskDataSource
    private val taskLocalDataSource: TaskDataSource
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    companion object {
        @Volatile
        private var INSTANCE: TaskRepository? = null

        fun getRepository(application: Application): TaskRepository {
            return INSTANCE ?: synchronized(this) {
                TaskRepository(application).also {
                    INSTANCE = it
                }
            }
        }
    }

    init {
        val database =
            Room.databaseBuilder(application.applicationContext, TaskDatabase::class.java, "tasks")
                .build()

        taskRemoteDataSource = TaskRemoteDataSource
        taskLocalDataSource = TaskLocalDataSource(database.taskDao())
    }

    suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>> {
        if (forceUpdate) {
            try {
                updateTasksFromRemoteDataSource()
            } catch (e: Exception) {
                return Result.Error(e)
            }
        }

        return taskLocalDataSource.getTasks()
    }

    suspend fun refreshTask() {
        updateTasksFromRemoteDataSource()
    }

    fun observeTask(taskId: String): LiveData<Result<Task>> {
        return taskLocalDataSource.observeTask(taskId)
    }

    /**
     * Relies on [getTasks] to fetch data and picks the task with the same ID.
     */
    suspend fun getTask(taskId: String, forceUpdate: Boolean = false): Result<Task> {
        if (forceUpdate) {
            updateTaskFromRemoteDatasource(taskId)
        }
        return taskLocalDataSource.getTask(taskId)
    }

    suspend fun saveTask(task: Task) {
        coroutineScope {
            launch { taskRemoteDataSource.completeTask(task) }
            launch { taskLocalDataSource.completeTask(task) }
        }
    }

    suspend fun completeTask(task: Task) {
        coroutineScope {
            launch { taskRemoteDataSource.completeTask(task) }
            launch { taskLocalDataSource.completeTask(task) }
        }
    }

    suspend fun activateTask(task: Task) = withContext<Unit>(ioDispatcher) {
        coroutineScope {
            launch { taskRemoteDataSource.activateTask(task) }
            launch { taskRemoteDataSource.activateTask(task) }
        }
    }

    suspend fun activateTask(taskId: String) = withContext(ioDispatcher) {
        (getTaskWithId(taskId) as? Result.Success)?.let {
            activateTask(it.data)
        }
    }

    suspend fun clearCompletedTasks() {
        coroutineScope {
            launch { taskRemoteDataSource.clearCompletedTasks() }
            launch { taskLocalDataSource.clearCompletedTasks() }
        }
    }

    suspend fun deleteAllTasks() {
        withContext(ioDispatcher) {
            coroutineScope {
                launch { taskRemoteDataSource.deleteAllTasks() }
                launch { taskLocalDataSource.deleteAllTasks() }
            }
        }
    }

    suspend fun deleteTask(taskId: String) {
        coroutineScope {
            launch { taskRemoteDataSource.deleteTask(taskId) }
            launch { taskLocalDataSource.deleteTask(taskId) }
        }
    }

    private suspend fun updateTasksFromRemoteDataSource() {
        val remoteTasks = taskRemoteDataSource.getTasks()

        if (remoteTasks is Result.Success) {
            taskLocalDataSource.deleteAllTasks()
            remoteTasks.data.forEach { task ->
                taskLocalDataSource.saveTask(task)
            }
        } else if (remoteTasks is Result.Error) {
            throw remoteTasks.exception
        }
    }

    private suspend fun updateTaskFromRemoteDatasource(taskId: String) {
        val remoteTask = taskRemoteDataSource.getTask(taskId)

        if (remoteTask is Result.Success) {
            taskLocalDataSource.saveTask(remoteTask.data)
        }
    }

    private suspend fun getTaskWithId(id: String): Result<Task> {
        return taskLocalDataSource.getTask(id)
    }

}