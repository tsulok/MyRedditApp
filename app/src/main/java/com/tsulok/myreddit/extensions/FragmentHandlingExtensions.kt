package com.tsulok.myreddit.extensions

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity

/**
 * Extension functions for fragments
 */

fun Fragment.replaceFragment(fragmentManager: FragmentManager, containerId: Int, fragment: Fragment, tag: String? = null) {
    fragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .commitAllowingStateLoss()
    fragmentManager.executePendingTransactions()
}

fun AppCompatActivity.replaceFragment(containerId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager
            .beginTransaction()
            .replace(containerId, fragment, tag)
            .commitAllowingStateLoss()
    supportFragmentManager.executePendingTransactions()
}