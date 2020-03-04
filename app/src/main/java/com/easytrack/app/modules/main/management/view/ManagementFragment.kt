package com.easytrack.app.modules.main.management.view

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.easytrack.app.R
import com.easytrack.app.databinding.FragmentManagementBinding
import com.easytrack.app.modules.common.view.BaseFragment
import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.modules.main.management.view.ManagementActionName.CREATE
import com.easytrack.app.modules.main.management.view.ManagementActionName.EDIT
import com.easytrack.app.modules.main.management.viewmodel.ManagementViewModel
import com.easytrack.app.modules.main.tasks.view.TaskListAdapter

class ManagementFragment :
    BaseFragment<EmptyInitialDataProvider, ManagementViewModel, FragmentManagementBinding>(),
    IManageTask {

    override fun addTask(id: Long, taskName: String, photo: String) {
        viewModel.addNewTask(id, taskName, photo)
    }

    override fun changeTask(id: Long, taskName: String, photo: String) {
        viewModel.updateTask(id, taskName, photo)
    }

    override fun deleteTask(id: Long) {
        viewModel.deleteTask(id)
    }

    override fun getLayoutId(): Int = R.layout.fragment_management

    override fun getViewModelClass(): Class<out ViewModel> = ManagementViewModel::class.java

    override fun getInitialDataProvider() = EmptyInitialDataProvider

    override fun setupUI() {
        super.setupUI()

        binding.taskListMgmt.layoutManager = LinearLayoutManager(requireContext())

        val adapter = TaskListAdapter { view, task ->
            if (view.id == R.id.taskCard)
                showDialog(ManageTaskDialog(EDIT, this, task))
        }
        viewModel.getTasks().observe(this, Observer { tasks -> adapter.submitList(tasks) })

        binding.taskListMgmt.adapter = adapter

        binding.addTaskCardView.setOnClickListener {
            showDialog(ManageTaskDialog(CREATE, this))
        }
    }
}

enum class ManagementActionName {
    CREATE, EDIT
}