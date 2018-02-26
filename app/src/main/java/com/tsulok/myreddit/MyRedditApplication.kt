package com.tsulok.myreddit

import android.app.Activity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import com.tsulok.myreddit.di.component.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import io.realm.Realm
import javax.inject.Inject

class MyRedditApplication : DaggerApplication(), HasActivityInjector {

    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)

        if (BuildConfig.DEBUG) {
            val formatStrategy = PrettyFormatStrategy.newBuilder()
                    .tag("MyRedditApp")
                    .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val appComponent = DaggerApplicationComponent.builder()
                .application(this)
                .build()
        appComponent.inject(this)
        return appComponent
    }

    override fun activityInjector(): DispatchingAndroidInjector<Activity> {
        return dispatchingActivityInjector
    }

//    override fun attachBaseContext(newBase: Context) {
//        super.attachBaseContext(newBase)
//        MultiDex.install(this)
//    }
}