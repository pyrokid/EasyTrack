package com.easytrack.app.data.repository

import androidx.lifecycle.LiveData
import com.easytrack.app.app.EasyTrackApplication.Companion.CURRENT_REPORT_ID
import com.easytrack.app.data.model.Task
import com.easytrack.app.data.model.Report
import com.easytrack.app.modules.database.task.TaskDao
import com.easytrack.app.modules.database.trackedperiod.ReportDao
import io.reactivex.Single

class TaskRepository(private val taskDao: TaskDao, private val reportDao: ReportDao) {

    fun saveTask(task: Task): Single<Task> = Single.create {
        taskDao.insertTask(task)
    }

    fun loadTasks() = taskDao.getEnabledTasks()

    fun disableAllTasks(): Single<List<Task>> = Single.create {
        taskDao.disableAllTasks()
    }

    fun deleteTask(id: Long): Single<Task> = Single.create {
        taskDao.deleteTaskByID(id)
    }

    fun updateTask(id: Long, taskName: String, photo: String): Single<Task> = Single.create {
        taskDao.updateTask(id, taskName, photo)
    }

    fun loadTaskByID(id: Long):LiveData<Task> = taskDao.getTaskByID(id)

    /**
     * Just a divider line between task repo and report repo
     */

    fun createReport(trackedPeriod: Report, task: Task): Single<Report> =
        Single.create {
            CURRENT_REPORT_ID = reportDao.createReport(trackedPeriod)
            reportDao.updateReportInfo(
                trackedPeriod.id,
                task.taskName,
                task.taskImage
            )
        }

    fun deleteReportById(id: Long): Single<Report> =
        Single.create {
            reportDao.deleteReportById(id)
        }

    fun updateStartTracking(id: Long, startPeriod: Long): Single<Report> =
        Single.create {
            reportDao.updateStartTracking(id, startPeriod)
        }

    fun updateStopTracking(id: Long, finishPeriod: Long): Single<Report> =
        Single.create {
            reportDao.updateStopTracking(id, finishPeriod)
        }

    fun deleteAllRecords(): Single<List<Report>> = Single.create {
        reportDao.deleteAllReports()
    }

    fun loadReports() = reportDao.getActiveReportsLiveData()
}