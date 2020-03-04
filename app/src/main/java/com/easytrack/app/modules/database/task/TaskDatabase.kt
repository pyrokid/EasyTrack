package com.easytrack.app.modules.database.task

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easytrack.app.data.model.Task

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {

        fun getDatabase(context: Context): TaskDatabase =
            Room.databaseBuilder(
                context,
                TaskDatabase::class.java,
                "task_database"
            ).build()
    }
}