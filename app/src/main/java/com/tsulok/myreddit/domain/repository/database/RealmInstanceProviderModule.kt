package com.tsulok.myreddit.domain.repository.database

import dagger.Binds
import dagger.Module

/**
 * Dagger module of realm instance provider
 */
@Module()
abstract class RealmInstanceProviderModule {

    @Binds
    abstract fun provideRealmInstanceProvider(realmInstanceProvider: RealmInstanceProvider): IRealmInstanceProvider
}