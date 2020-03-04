package com.easytrack.app.di

import android.content.Context
import com.easytrack.app.app.EasyTrackApplication
import com.easytrack.app.modules.database.task.TaskDatabaseModule
import com.easytrack.app.modules.database.trackedperiod.ReportDatabaseModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CommonModule::class,
        UIModule::class,
        TaskDatabaseModule::class,
        ReportDatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<EasyTrackApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
