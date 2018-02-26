package com.tsulok.myreddit.di.module

import android.support.v4.app.Fragment
import com.tsulok.myreddit.ui.stories.mainpage.posts.MainPagePostsListFragment
import com.tsulok.myreddit.ui.stories.mainpage.posts.MainPagePostsListFragmentComponent
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 */
@Module(subcomponents = [
    MainPagePostsListFragmentComponent::class
])
abstract class FragmentBindingModule {

    @Binds
    @IntoMap
    @FragmentKey(MainPagePostsListFragment::class)
    abstract fun bindMainFragmentInjectorFactor(builder: MainPagePostsListFragmentComponent.Builder)
            : AndroidInjector.Factory<out Fragment>
}