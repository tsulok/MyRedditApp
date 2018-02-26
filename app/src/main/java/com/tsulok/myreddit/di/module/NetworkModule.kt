package com.tsulok.myreddit.di.module

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.tsulok.myreddit.BuildConfig
import com.tsulok.myreddit.domain.network.services.ServiceGenerator
import com.tsulok.myreddit.domain.network.services.post.PostService
import com.tsulok.myreddit.domain.network.utility.ApplicationDateSerializer
import com.tsulok.myreddit.domain.network.utility.parser.NetworkErrorDIModule
import com.tsulok.myreddit.domain.provider.ISchedulerProvider
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * DI modules for network
 */
@Module(includes = [
    NetworkErrorDIModule::class
])
class NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    @Provides
    @Singleton
    fun provideMoshi(dateSerializer: ApplicationDateSerializer): Moshi {
        return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .add(Date::class.java, dateSerializer)
                .build()
    }

    @Provides
    @Singleton
    fun provideRXCallAdapter(schedulerProvider: ISchedulerProvider): RxJava2CallAdapterFactory
            = RxJava2CallAdapterFactory.createWithScheduler(schedulerProvider.io())

    @Provides
    @Singleton
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(logger)
                .build()
    }

    @Provides
    @Singleton
    fun providePostsService(serviceGenerator: ServiceGenerator): PostService {
        return serviceGenerator.createService(PostService::class.java)
    }
}