package com.tsulok.myreddit.architecture

import io.reactivex.disposables.CompositeDisposable

/**
 * Base view interface for MVP
 */
interface IBaseView

interface IBaseLoaderView : IBaseView {
    fun showLoader(message: String? = null)

    fun hideLoader()
}

/**
 * Base presenter interface for MVP
 */
interface IBasePresenter<in T> {
    /**
     * Binds presenter with a view when resumed. The Presenter will perform initialization here.
     * @param view the view associated with this presenter
     */
    fun takeView(view: T)

    /**
     * Drops the reference to the view when destroyed
     */
    fun dropView()

    /**
     * Notifies the presenter about the final ondestroy event
     */
    fun viewDestroyedCompletely()
}

/**
 * Base presenter implementation
 * Handles view refenrece
 */
open class BasePresenter<T : IBaseView> : IBasePresenter<T> {

    val disposeBag: CompositeDisposable = CompositeDisposable()

    open var view: T? = null

    override fun takeView(view: T) {
        this.view = view
    }

    override fun dropView() {
        this.view = null
    }

    override fun viewDestroyedCompletely() {
        disposeBag.dispose()
    }
}