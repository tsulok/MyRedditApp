package com.tsulok.myreddit.ui.base.list.viewholder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.tsulok.myreddit.ui.base.model.IBaseListType


/**
 * Base ViewHolder for list's viewholders
 * Registers the item clicks
 */
abstract class BaseViewHolder<in T : IBaseListType>(
        protected val view: View,
        protected var context: Context)
    : RecyclerView.ViewHolder(view) {

    var clickableView: View? = null // The view which can be clicked on the list

    /**
     * List item click listener interface
     */
    interface OnItemClickListener {

        /**
         * A list item is clicked on the list
         *
         * @param v        The parent view is clicked
         * @param position The clicked item position
         */
        fun listItemClicked(v: View, position: Int)
    }

    init {
        clickableView = view
    }

    abstract fun bind(data: T)
}