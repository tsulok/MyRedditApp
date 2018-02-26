package com.tsulok.myreddit.di.module

import android.app.Application
import android.content.Context
import com.tsulok.myreddit.di.ApplicationContext
import dagger.Binds
import dagger.Module

/**
 * DI modules for Application
 */
@Module
abstract class ApplicationModule {

    @ApplicationContext
    @Binds
    abstract fun bindContext(application: Application): Context
}