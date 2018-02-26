package com.tsulok.myreddit.di.module

import com.tsulok.myreddit.domain.provider.ISchedulerProvider
import com.tsulok.myreddit.domain.provider.SchedulerProvider
import com.tsulok.myreddit.domain.repository.database.RealmDatabaseProviderModule
import dagger.Binds
import dagger.Module

/**
 * DI modules for Providers
 */
@Module(includes = [
    RealmDatabaseProviderModule::class
])
abstract class ProviderModule {

    @Binds
    abstract fun provideSchedulerProvider(schedulerProvider: SchedulerProvider): ISchedulerProvider
}