package com.tsulok.myreddit.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.widget.Toolbar
import com.tsulok.myreddit.architecture.IBasePresenter
import com.tsulok.myreddit.architecture.IBaseView
import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Use as the base of the activities
 */
abstract class BaseActivity<in V, P> : DaggerAppCompatActivity()
        where V : IBaseView, P : IBasePresenter<V> {

    @Inject
    lateinit var presenter: P

    protected val disposeBag: CompositeDisposable = CompositeDisposable()

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.takeView(this as V)
        setContentView(loadContentId())
        initObjects()
    }

    @Suppress("UNCHECKED_CAST")
    override fun onResume() {
        super.onResume()
        presenter.takeView(this as V)
    }

    override fun onStop() {
        super.onStop()
        presenter.dropView()
    }

    /**
     * Loads the content layout id
     */
    @LayoutRes
    abstract fun loadContentId(): Int

    /**
     * Base initialization in onCreate
     */
    open fun initObjects() {
        loadToolbar()?.let { toolbar ->
            setSupportActionBar(toolbar)

            // Always show back button
            supportActionBar?.let {
                it.setDisplayHomeAsUpEnabled(true)
                it.setDisplayShowHomeEnabled(true)
            }

            // Init toolbar back handler
            toolbar.setNavigationOnClickListener { handleBackClicked() }
        }
    }

    override fun onDestroy() {
        presenter.viewDestroyedCompletely()
        disposeBag.dispose()
        super.onDestroy()
    }

    open fun handleBackClicked() {
        finish()
    }

    open fun loadToolbar(): Toolbar? = null
}