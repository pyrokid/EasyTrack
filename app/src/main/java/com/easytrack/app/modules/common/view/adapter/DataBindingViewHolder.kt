package com.easytrack.app.modules.common.view.adapter

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.easytrack.app.BR
import com.easytrack.app.R


class DataBindingViewHolder<T>(
    val binding: ViewDataBinding,
    private val clickListener: (view: View, item: T) -> Unit
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    init {
        itemView.setOnClickListener(this)
    }

    fun bind(item: T) {
        binding.root.setTag(R.id.tag_list_item, item)
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }

    override fun onClick(v: View) {
        val item: Any? = binding.root.getTag(R.id.tag_list_item)
        if (adapterPosition != RecyclerView.NO_POSITION && null != item) {
            clickListener.invoke(v, item as T)
        }
    }
}
