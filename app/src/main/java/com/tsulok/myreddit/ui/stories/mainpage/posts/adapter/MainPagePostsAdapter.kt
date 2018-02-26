package com.tsulok.myreddit.ui.stories.mainpage.posts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.tsulok.myreddit.di.ApplicationContext
import com.tsulok.myreddit.ui.base.list.adapter.BaseAdapter
import com.tsulok.myreddit.ui.base.list.viewholder.BaseViewHolder
import com.tsulok.myreddit.ui.stories.mainpage.posts.model.MainPagePostItem
import java.text.DateFormat
import javax.inject.Inject

/**
 * Adapter for listing devices
 */
class MainPagePostsAdapter
@Inject constructor(@ApplicationContext
                    context: Context) : BaseAdapter<MainPagePostItem>(context) {

    private val dateFormatter: DateFormat = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder<MainPagePostItem> {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)
        val viewHolder = MainPagePostsViewHolder(view, context, dateFormatter)
        prepareItemOnClick(viewHolder)
        return viewHolder
    }
}