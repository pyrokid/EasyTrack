package com.easytrack.app.modules.main.tasks.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import com.easytrack.app.data.model.Task
import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.common.viewmodel.BaseViewModel
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.utils.rx.RxSchedulers
import javax.inject.Inject

class TaskListViewModel @Inject constructor(
    rxSchedulers: RxSchedulers,
    private val context: Context,
    private val taskRepository: TaskRepository
) : BaseViewModel<EmptyInitialDataProvider>(rxSchedulers) {

    fun getTasks(): LiveData<List<Task>> = taskRepository.loadTasks()

}