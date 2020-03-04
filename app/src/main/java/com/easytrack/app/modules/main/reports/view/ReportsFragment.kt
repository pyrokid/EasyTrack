package com.easytrack.app.modules.main.reports.view

import com.easytrack.app.modules.common.viewmodel.EmptyInitialDataProvider
import com.easytrack.app.modules.main.reports.viewmodel.ReportsViewModel
import com.easytrack.app.databinding.FragmentReportsBinding
import com.easytrack.app.modules.common.view.BaseFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.Observer
import com.easytrack.app.R
import java.util.*

class ReportsFragment :
    BaseFragment<EmptyInitialDataProvider, ReportsViewModel, FragmentReportsBinding>(), IReportDialogEditor {

    override fun getLayoutId(): Int = R.layout.fragment_reports

    override fun getViewModelClass(): Class<out ViewModel> = ReportsViewModel::class.java

    override fun getInitialDataProvider() = EmptyInitialDataProvider

    lateinit var adapter: ReportAdapter

    override fun setupUI() {
        super.setupUI()
        binding.taskRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = ReportAdapter { view, item ->
            view.setOnClickListener {
                showDialog(EditReportsDialog(this, item))
            }
        }
        setReportAdapter()
    }

    private fun setReportAdapter(){
        viewModel.loadReports().observe(this, Observer { reports ->
            adapter.submitList(reports.reversed())
        })

        binding.taskRecyclerView.adapter = adapter
    }

    override fun updateStartPeriod(id: Long, date: Date){
        setReportAdapter()
        viewModel.updateStartPeriod(id, date)
    }

    override fun updateFinishPeriod(id: Long, date: Date){
        setReportAdapter()
        viewModel.updateFinishPeriod(id, date)
    }

    override fun deleteReportById(id: Long) {
        viewModel.deleteReportById(id)
    }

}

interface IReportDialogEditor{
    fun deleteReportById(id:Long)
    fun updateStartPeriod(id: Long, date: Date)
    fun updateFinishPeriod(id: Long, date: Date)
}