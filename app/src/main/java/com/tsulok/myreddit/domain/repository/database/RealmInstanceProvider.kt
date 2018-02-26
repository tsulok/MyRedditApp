package com.tsulok.myreddit.domain.repository.database

import android.os.Looper
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Provide realm instances based on configuration
 */

interface IRealmInstanceProvider {

    /**
     * Provides a Realm instance
     * If the thread is on the main, load the cached thread instance
     * Otherwise create a new with the configuration
     */
    fun provideRealmInstance(): Realm

    /**
     * Provides a new realm instance
     * @param configuration Used for creating a new realm instance, if not provided the default will be used
     */
    fun provideNewRealmInstance(configuration: RealmConfiguration? = null): Realm
}

@Singleton
class RealmInstanceProvider
@Inject constructor(private val realmConfiguration: RealmConfiguration) : IRealmInstanceProvider {

    private var mainThreadRealm: Realm? = null

    override fun provideRealmInstance(): Realm {

        // If the thread is on main thread
        // Then load the main
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (mainThreadRealm == null) {
                mainThreadRealm = provideNewRealmInstance()
            }
            mainThreadRealm?.let {
                return it
            }

            // Main thread realm is guaranteed by here
            assert(mainThreadRealm == null)

            // Fallback branch, create a new realm in production
            return provideNewRealmInstance()
        }

        return provideNewRealmInstance()
    }

    override fun provideNewRealmInstance(configuration: RealmConfiguration?): Realm {
        val usedConfig = configuration ?: realmConfiguration
        return Realm.getInstance(usedConfig)
    }
}