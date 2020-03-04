package com.easytrack.app.modules.main.home.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import com.easytrack.app.data.model.Report
import com.easytrack.app.data.model.Task
import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.common.viewmodel.BaseViewModel
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.utils.rx.RxSchedulers
import io.reactivex.Single
import javax.inject.Inject


class HomeViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val context: Context,
    private val taskRepository: TaskRepository
) : BaseViewModel<EmptyInitialDataProvider>(rxSchedulers) {

    fun createReport(taskId: Long, periodStart: Long, task: Task) {
        val report = Report(0, taskId, periodStart, null, false ,task.taskName, task.taskImage)
        action(taskRepository.createReport(report, task))
    }

    fun updateStopTracking(id: Long, stopTrackedPeriod: Long) {
        action(taskRepository.updateStopTracking(id, stopTrackedPeriod))
    }

    fun getTaskByID(id: Long): LiveData<Task> = taskRepository.loadTaskByID(id)
}