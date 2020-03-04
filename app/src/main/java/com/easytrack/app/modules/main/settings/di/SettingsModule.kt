package com.easytrack.app.modules.main.settings.di

import androidx.lifecycle.ViewModel
import com.easytrack.app.di.ActivityScope
import com.easytrack.app.di.ViewModelKey
import com.easytrack.app.modules.main.settings.viewmodel.SettingsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SettingsModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(viewModel: SettingsViewModel): ViewModel
}