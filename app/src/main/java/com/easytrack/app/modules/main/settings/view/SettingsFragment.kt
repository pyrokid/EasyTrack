package com.easytrack.app.modules.main.settings.view

import androidx.lifecycle.ViewModel
import com.easytrack.app.R
import com.easytrack.app.app.EasyTrackApplication.Companion.CURRENT_REPORT_ID
import com.easytrack.app.databinding.FragmentSettingsBinding
import com.easytrack.app.modules.common.view.BaseFragment
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.modules.main.home.view.HomeActivity
import com.easytrack.app.modules.main.home.view.StartBtnState.HIDDEN
import com.easytrack.app.modules.main.settings.viewmodel.SettingsViewModel


class SettingsFragment :
    BaseFragment<EmptyInitialDataProvider, SettingsViewModel, FragmentSettingsBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_settings

    override fun getViewModelClass(): Class<out ViewModel> = SettingsViewModel::class.java

    override fun getInitialDataProvider() = EmptyInitialDataProvider

    override fun setupUI() {
        super.setupUI()

        binding.clearTasksCard.setOnClickListener {
            viewModel.disableAllTasks()
            forceStopTracking()

        }

        binding.deleteReportsCard.setOnClickListener {
            viewModel.deleteAllRecords()
            forceStopTracking()

        }

        binding.deleteTaskAndReportsCard.setOnClickListener {
            viewModel.deleteAllTasksAndRecords()
            forceStopTracking()

        }
    }

    private fun forceStopTracking() {
        (activity as HomeActivity).removeTaskFromPanel()
        CURRENT_REPORT_ID = null
        (activity as HomeActivity).startButtonState = HIDDEN
        (activity as HomeActivity).currentTaskId = null
    }
}