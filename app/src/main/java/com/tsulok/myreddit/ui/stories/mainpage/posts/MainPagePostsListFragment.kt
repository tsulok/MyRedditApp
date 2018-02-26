package com.tsulok.myreddit.ui.stories.mainpage.posts

import android.net.Uri
import android.support.customtabs.CustomTabsIntent
import com.tsulok.myreddit.R
import com.tsulok.myreddit.ui.base.list.BaseListFragment
import com.tsulok.myreddit.ui.base.list.adapter.BaseAdapter
import com.tsulok.myreddit.ui.stories.mainpage.posts.adapter.MainPagePostsAdapter
import com.tsulok.myreddit.ui.stories.mainpage.posts.model.MainPagePostItem
import com.tsulok.myreddit.utility.helper.ToastHelper
import javax.inject.Inject


/**
 * Fragment which loading posts
 */
class MainPagePostsListFragment : BaseListFragment<MainPagePostItem,
        MainPagePostsContract.View, MainPagePostsContract.Presenter>(), MainPagePostsContract.View {

    @Inject lateinit var adapter: MainPagePostsAdapter
    @Inject lateinit var toastHelper: ToastHelper

    override fun initObjects() {
        super.initObjects()
        initHandlers()
        presenter.initialize()
    }

    private fun initHandlers() {
        setOnItemClickListener { _, position ->
            presenter.loadDetails(position)
        }

        onSwipeRefreshCalbackListener = object : OnSwipeRefreshCallbackListener {
            override fun onSwipeRefreshed() {
                presenter.forceSync()
            }
        }
    }

    override fun loadPosts(posts: List<MainPagePostItem>) {
        setSwipeRefreshFinished()
        adapter.removeAllItems()
        adapter.addItems(posts)
    }

    override fun loadDetails(url: String) {
        val context = context?.let { it } ?: return
        val intent = CustomTabsIntent.Builder()
                .setToolbarColor(context.resources.getColor(R.color.colorPrimary))
                .build()
        intent.launchUrl(context, Uri.parse(url))
    }

    override fun showNoCachedData() {
        toastHelper.makeToast(R.string.posts_no_cache)
    }

    override fun isPullToRefreshEnabled(): Boolean = true

    override fun getAdapter(): BaseAdapter<MainPagePostItem>? = adapter
}