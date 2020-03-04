package com.easytrack.app.modules.common.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class BaseListAdapter<Item>(
    diffCallback: DiffUtil.ItemCallback<Item>,
    private val itemClicked: (view: View, item: Item) -> Unit = { _, _ -> }
) : ListAdapter<Item, DataBindingViewHolder<Item>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<Item> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)

        return DataBindingViewHolder(binding, itemClicked)
    }

    /**
     * Return @LayoutRes; it's comfortable to build adapter with different viewHolders through sealed classes for DTO
     */
    abstract override fun getItemViewType(position: Int): Int

    override fun onBindViewHolder(holder: DataBindingViewHolder<Item>, position: Int) =
        holder.bind(getItem(position))
}
