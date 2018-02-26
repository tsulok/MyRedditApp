package com.tsulok.myreddit.ui.stories.mainpage.posts

import com.tsulok.myreddit.di.injector.BaseAndroidFragmentInjector
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Subcomponent injector for ChartFragment
 */
@Subcomponent(modules = [
    MainPagePostsListFragmentComponent.MainPagePostsListFragmentModule::class,
    BaseAndroidFragmentInjector.BaseFragmentProviderModule::class
])
interface MainPagePostsListFragmentComponent : BaseAndroidFragmentInjector<MainPagePostsListFragment> {

    @Module
    abstract class MainPagePostsListFragmentModule : BaseAndroidFragmentInjector.BaseFragmentModule<MainPagePostsListFragment>() {

        @Binds
        abstract fun provideMainPagePostsListFragmentPresenter(presenter: MainPagePostsListPresenter): MainPagePostsContract.Presenter
    }

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainPagePostsListFragment>()
}