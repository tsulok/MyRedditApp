package com.tsulok.myreddit.ui.base

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tsulok.myreddit.architecture.IBasePresenter
import com.tsulok.myreddit.architecture.IBaseView
import dagger.android.support.DaggerFragment
import kotterknife.KotterKnife
import javax.inject.Inject

/**
 * Use as the base of the fragments
 */
abstract class BaseFragment<in V, P> : DaggerFragment(), IFragmentFactory
        where V : IBaseView, P : IBasePresenter<V> {

    @Inject
    lateinit var presenter: P

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(loadContentId(), container, false)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.takeView(this as V)
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

    override fun onDestroyView() {
        presenter.dropView()
        KotterKnife.reset(this)
        super.onDestroyView()
    }

    /**
     * Loads the content layout id
     */
    @LayoutRes
    abstract fun loadContentId(): Int

    /**
     * Base initialization in onCreate
     */
    abstract fun initObjects()

    override fun getFragment(): BaseFragment<*, *> = this
}