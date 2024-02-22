package com.ipritdev.androidtesting.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.ipritdev.androidtesting.data.Result
import com.ipritdev.androidtesting.data.Task
import com.ipritdev.androidtesting.data.source.TaskDataSource
import com.ipritdev.androidtesting.data.Result.Success
import com.ipritdev.androidtesting.data.Result.Error
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskLocalDataSource internal constructor(
    private val taskDao: TaskDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : TaskDataSource {

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return taskDao.observeTasks().map {
            Success(it)
        }
    }

    override suspend fun getTasks(): Result<List<Task>> = withContext(ioDispatcher) {
        return@withContext try {
            Success(taskDao.getTasks())
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun refreshTasks() {
        //NO-OP
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return taskDao.observeTaskById(taskId).map {
            Success(it)
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> = withContext(ioDispatcher) {
        return@withContext try {
            val task = taskDao.getTaskById(taskId)
            if (task != null) {
                return@withContext Success(task)
            } else {
                return@withContext Error(Exception("Task not found"))
            }
        } catch (e: Exception) {
            Error(e)
        }
    }

    override suspend fun refreshTask(taskId: String) {
        //NO-OP
    }

    override suspend fun saveTask(task: Task) = withContext(ioDispatcher) {
        taskDao.insertTask(task)
    }

    override suspend fun completeTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(task.id, true)
    }

    override suspend fun completeTask(taskId: String) {
        taskDao.updateCompleted(taskId, true)
    }

    override suspend fun activateTask(task: Task) = withContext(ioDispatcher) {
        taskDao.updateCompleted(task.id, false)
    }

    override suspend fun activateTask(taskId: String) {
        taskDao.updateCompleted(taskId, false)
    }

    override suspend fun clearCompletedTasks() = withContext<Unit>(ioDispatcher) {
        taskDao.deleteCompletedTasks()
    }

    override suspend fun deleteAllTasks() {
        taskDao.deleteTasks()
    }

    override suspend fun deleteTask(taskId: String) {
        taskDao.deleteByTaskId(taskId)
    }

}