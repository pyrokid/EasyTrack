package com.easytrack.app.di


import androidx.lifecycle.ViewModelProvider
import com.easytrack.app.modules.main.home.di.HomeModule
import com.easytrack.app.modules.main.home.view.HomeActivity
import com.easytrack.app.modules.main.management.di.ManagementModule
import com.easytrack.app.modules.main.management.view.ManagementFragment
import com.easytrack.app.modules.main.reports.di.ReportsModule
import com.easytrack.app.modules.main.reports.view.ReportsFragment
import com.easytrack.app.modules.main.settings.di.SettingsModule
import com.easytrack.app.modules.main.settings.view.SettingsFragment
import com.easytrack.app.modules.main.tasklist.di.TaskListModule
import com.easytrack.app.modules.main.tasks.view.TaskListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author EEV
 */
@Module
abstract class UIModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @ActivityScope
    @ContributesAndroidInjector(modules = [HomeModule::class])
    abstract fun contributeHomeActivity(): HomeActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [TaskListModule::class])
    abstract fun contributeTaskListFragment(): TaskListFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [SettingsModule::class])
    abstract fun contributeSettingsFragment(): SettingsFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [ManagementModule::class])
    abstract fun contributeManagementFragment(): ManagementFragment

    @ActivityScope
    @ContributesAndroidInjector(modules = [ReportsModule::class])
    abstract fun contributeReportsFragment(): ReportsFragment
}