package com.tsulok.myreddit.ui.base

/**
 * Interface for model classes to return their own detailed fragment
 */
interface IFragmentFactory {
    /**
     * Creates a fragment for the actual model
     * @return A new fragment
     */
    fun getFragment(): BaseFragment<*, *>
}