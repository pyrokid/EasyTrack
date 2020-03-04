package com.easytrack.app.modules.main.tasks.view

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import com.easytrack.app.R
import com.easytrack.app.data.model.Task
import com.easytrack.app.modules.common.view.adapter.BaseListAdapter
import com.easytrack.app.modules.common.view.adapter.DataBindingViewHolder

class TaskListAdapter(
    onTaskClicked: (view: View, item: Task) -> Unit
) : BaseListAdapter<Task>(DiffCallback(), onTaskClicked) {
    class DiffCallback : DiffUtil.ItemCallback<Task>() {

        override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
            oldItem == newItem
    }

    override fun getItemViewType(position: Int): Int = R.layout.item_activity_card

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataBindingViewHolder<Task> {
        return super.onCreateViewHolder(parent, viewType).apply {
            val taskImage = binding.root.findViewById<ImageView>(R.id.editImage)
        }
    }
}

