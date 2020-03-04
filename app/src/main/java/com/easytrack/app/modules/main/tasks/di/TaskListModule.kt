package com.easytrack.app.modules.main.tasklist.di

import androidx.lifecycle.ViewModel
import com.easytrack.app.di.ActivityScope
import com.easytrack.app.di.ViewModelKey
import com.easytrack.app.modules.main.tasks.viewmodel.TaskListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TaskListModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(TaskListViewModel::class)
    abstract fun bindViewModel(viewModel: TaskListViewModel): ViewModel
}