package com.easytrack.app.modules.main.tasks.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.easytrack.app.R
import com.easytrack.app.databinding.FragmentActivitiesBinding
import com.easytrack.app.modules.common.view.BaseFragment
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.modules.main.home.view.HomeActivity
import com.easytrack.app.modules.main.tasks.viewmodel.TaskListViewModel

class TaskListFragment :
    BaseFragment<EmptyInitialDataProvider, TaskListViewModel, FragmentActivitiesBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_activities

    override fun getViewModelClass(): Class<out ViewModel> = TaskListViewModel::class.java

    override fun getInitialDataProvider() = EmptyInitialDataProvider

    override fun setupUI() {
        super.setupUI()

        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TaskListAdapter { view, item ->
            if (view.id == R.id.taskCard)
                (activity as HomeActivity).sendTaskToTrackPanel(item.id)
        }
        viewModel.getTasks().observe(this, Observer { tasks -> adapter.submitList(tasks) })

        binding.taskRecyclerView.adapter = adapter
    }
}
