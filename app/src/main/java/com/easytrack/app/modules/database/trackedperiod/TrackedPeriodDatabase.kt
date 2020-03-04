package com.easytrack.app.modules.database.trackedperiod

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.easytrack.app.data.model.Report
import com.easytrack.app.data.model.Task

@Database(entities = [Report::class, Task::class], version = 1, exportSchema = false)
abstract class ReportDatabase : RoomDatabase() {

    abstract fun trackedPeriodDao(): ReportDao

    companion object {

        fun getDatabase(context: Context): ReportDatabase =
            Room.databaseBuilder(
                context,
                ReportDatabase::class.java,
                "report_database"
            ).build()
    }
}