package com.easytrack.app.modules.main.reports.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.easytrack.app.R
import com.easytrack.app.data.model.Report
import com.easytrack.app.modules.common.view.adapter.BaseListAdapter
import com.easytrack.app.modules.common.view.adapter.DataBindingViewHolder

class ReportAdapter(
    onReportClicked: (view: View, item: Report) -> Unit
) : BaseListAdapter<Report>(DiffCallback(), onReportClicked) {
    class DiffCallback : DiffUtil.ItemCallback<Report>() {

        override fun areItemsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Report, newItem: Report): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_task_report
}