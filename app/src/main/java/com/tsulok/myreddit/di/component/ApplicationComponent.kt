package com.tsulok.myreddit.di.component

import android.app.Application
import android.content.Context
import com.tsulok.myreddit.MyRedditApplication
import com.tsulok.myreddit.di.ApplicationContext
import com.tsulok.myreddit.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Component for supported DI components
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    ActivityBindingModule::class,
    FragmentBindingModule::class,
    AndroidInjectionModule::class,
    AndroidSupportInjectionModule::class,
    RepositoryModule::class,
    InteractorModule::class,
    NetworkModule::class,
    ProviderModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {

    @ApplicationContext
    fun context(): Context

    fun inject(application: MyRedditApplication)

    override fun inject(instance: DaggerApplication?)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }
}