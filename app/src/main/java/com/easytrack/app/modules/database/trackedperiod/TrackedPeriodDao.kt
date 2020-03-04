package com.easytrack.app.modules.database.trackedperiod

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.easytrack.app.data.model.Report

@Dao
interface ReportDao {

    @Query("UPDATE reports SET isReportEnabled = 0")
    fun deleteAllReports()

    @Query("SELECT * FROM reports WHERE isReportEnabled = 1")
    fun getActiveReportsLiveData(): LiveData<List<Report>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createReport(trackedPeriod: Report): Long

    @Query("UPDATE reports SET isReportEnabled = 0 WHERE id = :id")
    fun deleteReportById(id: Long)

    @Query("UPDATE reports SET periodStart=:periodStart WHERE id=:id")
    fun updateStartTracking(id: Long, periodStart: Long)

    @Query("UPDATE reports SET periodFinish=:periodFinish, isReportEnabled= 1 WHERE id=:id")
    fun updateStopTracking(id: Long, periodFinish: Long)

    @Query("UPDATE reports SET taskName=:taskName, taskImage=:taskImage WHERE id=:trackedPeriodId")
    fun updateReportInfo(trackedPeriodId: Long, taskName: String, taskImage: String)
}
