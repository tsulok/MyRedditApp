package com.tsulok.myreddit.ui.stories.mainpage.posts

import com.tsulok.myreddit.architecture.IBasePresenter
import com.tsulok.myreddit.architecture.IBaseView
import com.tsulok.myreddit.ui.stories.mainpage.posts.model.MainPagePostItem

/**
 * This specifies the contract between the view and the presenter.
 */

interface MainPagePostsContract {

    /**
     * Updates on the UI from the presenter
     */
    interface View : IBaseView {
        fun loadPosts(posts: List<MainPagePostItem>)
        fun showNoCachedData()
        fun loadDetails(url: String)
    }

    /**
     * Actions on the presenter
     */
    interface Presenter : IBasePresenter<View> {
        fun initialize()
        fun loadDetails(position: Int)
        fun forceSync()
    }
}