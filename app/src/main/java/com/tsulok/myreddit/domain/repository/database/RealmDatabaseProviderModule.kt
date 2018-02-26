package com.tsulok.myreddit.domain.repository.database

import com.tsulok.myreddit.BuildConfig
import dagger.Module
import dagger.Provides
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton

/**
 * Provider for realms
 */
@Module
class RealmDatabaseProviderModule {

    @Singleton
    @Provides
    fun provideRealmConfiguration(): RealmConfiguration {
        val configBuilder = RealmConfiguration.Builder()
                .name("myRedditClient.realm")
                .schemaVersion(1)

        // Delete realm if migration is needed only in debug builds
        if (BuildConfig.DEBUG) {
            configBuilder.deleteRealmIfMigrationNeeded()
        }

        return configBuilder.build()
    }

    @Provides
    fun provideRealmInstance(realmConfiguration: RealmConfiguration): Realm {
        return Realm.getInstance(realmConfiguration)
    }
}