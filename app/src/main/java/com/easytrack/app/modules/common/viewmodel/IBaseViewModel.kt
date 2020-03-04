package com.easytrack.app.modules.common.viewmodel


import androidx.lifecycle.LiveData
import com.easytrack.app.modules.common.data.UIState
import com.easytrack.app.utils.ui.Event

interface IBaseViewModel<P : InitialDataProvider> {

    fun provideInitialData(provider: P)

    fun getUIState(): LiveData<UIState>
    fun getShowErrorConnectionDialogLiveData(): LiveData<Event<Boolean>>
    fun setDefaultUIState()
}
