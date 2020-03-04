package com.easytrack.app.di

import android.content.Context
import com.easytrack.app.data.repository.TaskRepository
import com.easytrack.app.modules.database.task.TaskDao
import com.easytrack.app.modules.database.trackedperiod.ReportDao
import com.easytrack.app.utils.rx.RxSchedulers
import com.easytrack.app.utils.rx.RxSchedulersImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class CommonModule {

    @Provides
    @Singleton
    fun provideRxSchedulers(): RxSchedulers = RxSchedulersImpl()

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context) =
        context.getSharedPreferences("tender", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun provideTaskRepository(
        taskDao: TaskDao,
        reportDao: ReportDao
    ) = TaskRepository(taskDao, reportDao)
}