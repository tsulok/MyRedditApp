package com.tsulok.myreddit.di.injector

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.tsulok.myreddit.di.ActivityContext
import com.tsulok.myreddit.di.ChildFragmentManager
import com.tsulok.myreddit.di.SupportFragmentManager
import com.tsulok.myreddit.di.module.UIHelperModule
import com.tsulok.myreddit.ui.base.BaseActivity
import com.tsulok.myreddit.ui.base.BaseFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity

/**
 * Base activity injector
 * Activity subComponent injectors should inherit this
 *
 * Example usage:
 *
 *  @Subcomponent(modules = arrayOf(XYZActivityComponent.XYZActivityModule::class))
 *  interface XYZActivitySubComponent : BaseAndroidActivityInjector<XYZActivity> {
 *
 *  @Module
 *  abstract class XYZActivityModule : BaseAndroidActivityInjector.BaseActivityModule<XYZActivity>()
 *
 *  @Subcomponent.Builder
 *  abstract class Builder : AndroidInjector.Builder<XYZActivity>()
}
 */
interface BaseAndroidActivityInjector<T : BaseActivity<*, *>> : AndroidInjector<T> {

    @Module(includes = arrayOf(UIHelperModule::class))
    abstract class BaseActivityModule<in T : BaseActivity<*, *>> {

        @ActivityContext
        @Binds
        abstract fun provideContext(activity: T): Context

        @Binds
        abstract fun provideActivity(activity: T): DaggerAppCompatActivity
    }

    @Module
    class BaseActivityProviderModule {
        @Provides
        @SupportFragmentManager
        fun provideSupportFragmentManager(activity: DaggerAppCompatActivity): FragmentManager = activity.supportFragmentManager
    }
}

/**
 * Base fragment injector
 * Fragment subComponent injectors should inherit this
 *
 * Example usage:
 *
 *  @Subcomponent(modules = arrayOf(XYZFragmentComponent.XYZFragmentModule::class))
 *  interface XYZFragmentSubComponent : BaseAndroidFragmentInjector<XYZFragment> {
 *
 *  @Module
 *  abstract class XYZFragmentModule : BaseAndroidFragmentInjector.BaseFragmentModule<XYZFragment>()
 *
 *  @Subcomponent.Builder
 *  abstract class Builder : AndroidInjector.Builder<XYZFragment>()
}
 */
interface BaseAndroidFragmentInjector<T : BaseFragment<*, *>> : AndroidInjector<T> {

    @Module(includes = arrayOf(UIHelperModule::class))
    abstract class BaseFragmentModule<in T : BaseFragment<*, *>> {

        @Binds
        abstract fun provideFragment(fragment: T): Fragment
    }

    @Module
    class BaseFragmentProviderModule {
        @Provides
        @ChildFragmentManager
        fun provideChildFragmentManager(fragment: Fragment): FragmentManager = fragment.childFragmentManager

        @Provides
        @ActivityContext
        fun provideActivityContect(fragment: Fragment): Context = fragment.activity!!
    }
}