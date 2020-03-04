package com.easytrack.app.modules.main.management.viewmodel

import androidx.lifecycle.LiveData
import com.easytrack.app.data.model.Task
import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.common.viewmodel.BaseViewModel
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.utils.rx.RxSchedulers
import javax.inject.Inject

class ManagementViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val taskRepository: TaskRepository
) : BaseViewModel<EmptyInitialDataProvider>(rxSchedulers) {

    fun getTasks(): LiveData<List<Task>> = taskRepository.loadTasks()


    fun addNewTask(id: Long, taskName: String, photo: String) {
        val task = Task(id, taskName, photo, true)
        action(taskRepository.saveTask(task))
    }

    fun updateTask(id: Long, taskName: String, photo: String) {
        action(taskRepository.updateTask(id, taskName, photo))
    }

    fun deleteTask(id: Long) {
        action(taskRepository.deleteTask(id))
    }
}