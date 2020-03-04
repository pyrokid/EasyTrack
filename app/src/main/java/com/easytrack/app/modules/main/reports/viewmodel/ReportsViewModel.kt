package com.easytrack.app.modules.main.reports.viewmodel

import androidx.lifecycle.LiveData
import com.easytrack.app.data.model.Report
import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.common.viewmodel.BaseViewModel
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.utils.rx.RxSchedulers
import java.util.*
import javax.inject.Inject

class ReportsViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val taskRepository: TaskRepository
) : BaseViewModel<EmptyInitialDataProvider>(rxSchedulers) {

    fun loadReports(): LiveData<List<Report>> = taskRepository.loadReports()

    fun updateStartPeriod(id: Long, date: Date) {
        action(taskRepository.updateStartTracking(id, Report.convertDateToLong(date)))
    }

    fun updateFinishPeriod(id: Long, date: Date) {
        action(taskRepository.updateStopTracking(id, Report.convertDateToLong(date)))
    }

    fun deleteReportById(id: Long) {
        action(taskRepository.deleteReportById(id))
    }
}