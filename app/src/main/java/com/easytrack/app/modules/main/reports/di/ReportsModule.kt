package com.easytrack.app.modules.main.reports.di

import androidx.lifecycle.ViewModel
import com.easytrack.app.di.ActivityScope
import com.easytrack.app.di.ViewModelKey
import com.easytrack.app.modules.main.reports.viewmodel.ReportsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ReportsModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(ReportsViewModel::class)
    abstract fun bindViewModel(viewModel: ReportsViewModel): ViewModel
}