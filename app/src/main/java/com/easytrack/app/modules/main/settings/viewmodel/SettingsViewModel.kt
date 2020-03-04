package com.easytrack.app.modules.main.settings.viewmodel

import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.common.viewmodel.BaseViewModel
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.utils.rx.RxSchedulers
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val taskRepository: TaskRepository
) : BaseViewModel<EmptyInitialDataProvider>(rxSchedulers) {

    fun disableAllTasks() {
        action(taskRepository.disableAllTasks())
    }

    fun deleteAllRecords() {
        action(taskRepository.deleteAllRecords())
    }

    fun deleteAllTasksAndRecords() {
        action(taskRepository.deleteAllRecords())
        action(taskRepository.disableAllTasks())
    }
}