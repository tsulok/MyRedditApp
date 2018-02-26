package com.tsulok.myreddit.ui.base.list

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import com.tsulok.myreddit.R
import com.tsulok.myreddit.architecture.IBasePresenter
import com.tsulok.myreddit.architecture.IBaseView
import com.tsulok.myreddit.ui.base.BaseFragment
import com.tsulok.myreddit.ui.base.list.adapter.BaseAdapter
import com.tsulok.myreddit.ui.base.list.viewholder.BaseViewHolder
import com.tsulok.myreddit.ui.base.model.IBaseListType
import kotterknife.bindView


/**
 * Base class for listFragment which extends Base
 */
abstract class BaseListFragment<T, V, P> : BaseFragment<V, P>()
        where T : IBaseListType, V : IBaseView, P : IBasePresenter<V> {

    protected val recyclerView: RecyclerView by bindView(R.id.listView)
    protected val swipeRefreshLayout: SwipeRefreshLayout by bindView(R.id.swipeRefreshLayout)
    protected val listOverlayContainer: FrameLayout by bindView(R.id.listOverlayContainer)

    // For swipe & move actions
    protected var onSwipeRefreshCalbackListener: OnSwipeRefreshCallbackListener? = null
    protected var swipeBackgroundColor: Int = 0

    abstract fun getAdapter(): BaseAdapter<T>?

    override fun loadContentId(): Int = R.layout.base_list

    override fun initObjects() {
        val adapter = getAdapter()?.let { it } ?: throw RuntimeException("RecyclerView is not properly initialized")

        recyclerView.layoutManager = getLayoutManager()
        recyclerView.adapter = adapter

        if (isPullToRefreshEnabled()) {
            swipeRefreshLayout.setOnRefreshListener {
                onSwipeRefreshCalbackListener?.onSwipeRefreshed() ?: run {
                    setSwipeRefreshFinished()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setSwipeRefreshFinished()
    }

    open fun addOverlayView(view: View) {
        listOverlayContainer.addView(view)
    }

    open fun hideOverlayView() {
        listOverlayContainer.visibility = View.GONE
    }

    open fun showOverlayView() {
        listOverlayContainer.visibility = View.VISIBLE
    }

    open fun updateOverlayView() {
        if (getAdapter() == null || getAdapter()?.itemCount == 0) {
            showOverlayView()
        } else {
            hideOverlayView()
        }
    }

    open fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(recyclerView.context)
    }

    fun setOnItemClickListener(handler: (view: View, position: Int) -> Unit) {
        getAdapter()?.onItemClickListener = object : BaseViewHolder.OnItemClickListener {
            override fun listItemClicked(v: View, position: Int) = handler(v, position)
        }
    }

    open fun isPullToRefreshEnabled(): Boolean = false

    fun setSwipeRefreshFinished() {
        swipeRefreshLayout.isRefreshing = false
    }

    interface OnSwipeRefreshCallbackListener {
        fun onSwipeRefreshed()
    }
}