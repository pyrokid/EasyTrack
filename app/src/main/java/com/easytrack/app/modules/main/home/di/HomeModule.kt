package com.easytrack.app.modules.main.home.di

import androidx.lifecycle.ViewModel
import com.easytrack.app.di.ActivityScope
import com.easytrack.app.di.ViewModelKey
import com.easytrack.app.modules.main.home.viewmodel.HomeViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ActivityScope
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}