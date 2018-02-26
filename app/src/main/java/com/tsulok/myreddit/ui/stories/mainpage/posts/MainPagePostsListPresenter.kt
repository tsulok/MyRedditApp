package com.tsulok.myreddit.ui.stories.mainpage.posts

import com.orhanobut.logger.Logger
import com.tsulok.myreddit.architecture.BasePresenter
import com.tsulok.myreddit.domain.interactor.posts.IPostsInteractor
import com.tsulok.myreddit.domain.model.PostItem
import com.tsulok.myreddit.ui.stories.mainpage.posts.model.MainPagePostItem
import javax.inject.Inject

/**
 * Presenter for MainPagePostsListPresenter
 */

class MainPagePostsListPresenter
@Inject
constructor(private val postsInteractor: IPostsInteractor) :
        BasePresenter<MainPagePostsContract.View>(), MainPagePostsContract.Presenter {

    private var items: MutableList<PostItem> = ArrayList()

    private fun loadPosts() {
        postsInteractor.listPosts()
                .doOnNext { items ->
                    if (items.isEmpty()) {
                        view?.showNoCachedData()
                    }
                }
                .doOnNext {
                    items.clear()
                    items.addAll(it)
                }
                .subscribe({ items ->
                    view?.loadPosts(items.map { MainPagePostItem(it) })
                }, { error ->
                    Logger.e("Error $error")
                })
                .also { disposable -> disposeBag.add(disposable) }
    }

    override fun initialize() {
        loadPosts()
    }

    override fun loadDetails(position: Int) {
        items[position].url?.let { view?.loadDetails(it) }
    }

    override fun forceSync() {
        loadPosts()
    }
}