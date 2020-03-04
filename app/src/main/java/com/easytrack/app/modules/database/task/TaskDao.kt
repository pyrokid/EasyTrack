package com.easytrack.app.modules.database.task

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easytrack.app.data.model.Task
import io.reactivex.Single

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    fun getSyncTasks(): List<Task>

    @Query("SELECT * FROM tasks")
    fun getTasksLiveData(): LiveData<List<Task>>

    @Query("SELECT * FROM tasks WHERE isTaskEnabled= 1")
    fun getEnabledTasks(): LiveData<List<Task>>

    @Query("UPDATE tasks SET isTaskEnabled=0")
    fun disableAllTasks()

    @Query("SELECT * FROM tasks WHERE id=:id")
    fun getTaskByID(id: Long): LiveData<Task>

    @Query("UPDATE tasks SET taskName=:taskName, taskImage=:photo WHERE id= :id")
    fun updateTask(id: Long, taskName: String, photo: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertTask(task: Task)

    @Query("UPDATE tasks SET isTaskEnabled=0 WHERE id = :id")
    fun deleteTaskByID(id: Long)
}