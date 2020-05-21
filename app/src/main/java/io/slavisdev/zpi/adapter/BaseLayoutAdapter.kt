package com.softroids.profracht.adapter

abstract class BaseLayoutAdapter(private val layoutId: Int, access: Any?) : BaseAdapter(access) {

    override fun getLayoutIdForPosition(position: Int): Int {
        return layoutId
    }
}