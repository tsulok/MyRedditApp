package com.tsulok.myreddit.di.module

import android.app.Activity
import com.tsulok.myreddit.ui.stories.mainpage.MainActivitySubComponent
import com.tsulok.myreddit.ui.stories.mainpage.MainPageActivity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap


/**
 * We want Dagger.Android to create a Subcomponent which has a parent Component of whichever module ActivityBindingModule is on,
 * in our case that will be AppComponent. The beautiful part about this setup is that you never need to tell AppComponent that it is going to have all these subcomponents
 * nor do you need to tell these subcomponents that AppComponent exists.
 */
@Module(subcomponents = [
    MainActivitySubComponent::class
])
abstract class ActivityBindingModule {

    @Binds
    @IntoMap
    @ActivityKey(MainPageActivity::class)
    abstract fun bindMainActivityInjectorFactor(builder: MainActivitySubComponent.Builder): AndroidInjector.Factory<out Activity>
}
