package com.easytrack.app.modules.main.management.di

import androidx.lifecycle.ViewModel
import com.easytrack.app.di.ActivityScope
import com.easytrack.app.di.ViewModelKey
import com.easytrack.app.modules.main.management.viewmodel.ManagementViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ManagementModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(ManagementViewModel::class)
    abstract fun bindViewModel(viewModel: ManagementViewModel): ViewModel
}