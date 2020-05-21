package com.softroids.profracht.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter(private val access: Any?) : RecyclerView.Adapter<BaseAdapter.ViewHolder>() {

    protected abstract fun getObjForPosition(position: Int): Any

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), viewType, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val obj = getObjForPosition(position)
        holder.bind(obj, access)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    class ViewHolder(private val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(obj: Any, access: Any?) {
            binding.setVariable(BR.obj, obj)
            if (access != null) {
                binding.setVariable(BR.access, access)
            }
            binding.executePendingBindings()
        }
    }
}