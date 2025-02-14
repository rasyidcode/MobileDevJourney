package com.ipritdev.androidtesting.data.source.remote

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.ipritdev.androidtesting.data.Result
import com.ipritdev.androidtesting.data.Result.Success
import com.ipritdev.androidtesting.data.Result.Error
import com.ipritdev.androidtesting.data.Task
import com.ipritdev.androidtesting.data.source.TaskDataSource
import kotlinx.coroutines.delay

/**
 * Implementation of the data source that adds a latency simulating network.
 */
object TaskRemoteDataSource : TaskDataSource {

    private const val SERVICE_LATENCY_IN_MILLIS = 2000L

    private var TASKS_SERVICE_DATA = LinkedHashMap<String, Task>(2)

    init {
        addTask("Build tower in Pisa", "Ground looks good, no foundation work required.")
        addTask("Finish bridge in Tacoma", "Found awesome girders at half the cost!")
    }

    private val observableTasks = MutableLiveData<Result<List<Task>>>()

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        return observableTasks
    }

    override suspend fun getTasks(): Result<List<Task>> {
        // Simulate network by delaying the execution.
        val tasks = TASKS_SERVICE_DATA.values.toList()
        delay(SERVICE_LATENCY_IN_MILLIS)
        return Success(tasks)
    }

    @SuppressLint("NullSafeMutableLiveData")
    override suspend fun refreshTasks() {
        observableTasks.value = getTasks()
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        return observableTasks.map {
            when (it) {
                is Result.Loading -> Result.Loading
                is Error -> Error(it.exception)
                is Success -> {
                    val task =
                        it.data.firstOrNull() { task -> task.id == taskId } ?: return@map Error(
                            Exception("Not found")
                        )
                    Success(task)
                }
            }
        }
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        delay(SERVICE_LATENCY_IN_MILLIS)
        TASKS_SERVICE_DATA[taskId]?.let {
            return Success(it)
        }

        return Error(Exception("Task not found"))
    }

    override suspend fun refreshTask(taskId: String) {
        refreshTasks()
    }

    override suspend fun saveTask(task: Task) {
        TASKS_SERVICE_DATA[task.id] = task
    }

    override suspend fun completeTask(task: Task) {
        val completedTask = Task(task.title, task.description, true, task.id)
        TASKS_SERVICE_DATA[task.id] = completedTask
    }

    override suspend fun completeTask(taskId: String) {
        // Not required for the remote data source
    }

    override suspend fun activateTask(task: Task) {
        val activateTask = Task(task.title, task.description, false, task.id)
        TASKS_SERVICE_DATA[task.id] = activateTask
    }

    override suspend fun activateTask(taskId: String) {
        // Not required for the remote data source
    }

    override suspend fun clearCompletedTasks() {
        TASKS_SERVICE_DATA = TASKS_SERVICE_DATA.filterValues {
            !it.isCompleted
        } as LinkedHashMap<String, Task>
    }

    override suspend fun deleteAllTasks() {
        TASKS_SERVICE_DATA.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        TASKS_SERVICE_DATA.remove(taskId)
    }

    private fun addTask(title: String, description: String) {
        val newTask = Task(title, description)
        TASKS_SERVICE_DATA[newTask.id] = newTask
    }

}