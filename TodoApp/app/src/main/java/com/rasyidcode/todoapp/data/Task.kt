package com.rasyidcode.todoapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

/**
 * Immutable model for a Task. In order to compile with Room, we can't use @JvmOverloads to
 * generate multiple constructors.
 *
 * @param id id of the tasks
 * @param title title of the task
 * @param description description of the task
 * @param isCompleted whether or not this task is completed
 */
@Entity(tableName = "tasks")
data class Task @JvmOverloads constructor(
    @PrimaryKey @ColumnInfo("entryId")
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title")
    val title: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
    @ColumnInfo(name = "completed")
    val isCompleted: Boolean = false
) {

    val titleForList: String
        get() = title.ifEmpty { description }

    val isActive: Boolean
        get() = !isCompleted

    val isEmpty
        get() = title.isEmpty() || description.isEmpty()

}
