package com.tsulok.myreddit.ui.stories.mainpage

import com.tsulok.myreddit.di.injector.BaseAndroidActivityInjector
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector


/**
 * Subcomponent injector for MainActivity
 */
@Subcomponent(modules = arrayOf(MainActivitySubComponent.MainActivityModule::class))
interface MainActivitySubComponent : BaseAndroidActivityInjector<MainPageActivity> {

    @Module
    abstract class MainActivityModule : BaseAndroidActivityInjector.BaseActivityModule<MainPageActivity>() {

        @Binds
        abstract fun provideMainPresenter(pagePresenter: MainPagePresenter): MainPageContract.Presenter
    }

    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainPageActivity>()
}