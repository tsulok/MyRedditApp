package com.tsulok.myreddit.domain.network.utility

import com.orhanobut.logger.Logger
import com.tsulok.myreddit.domain.network.model.RetrofitException
import com.tsulok.myreddit.domain.network.utility.parser.INetworkErrorCheckerParser
import com.tsulok.myreddit.domain.provider.ISchedulerProvider
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.lang.reflect.Type
import javax.inject.Inject


/**
 * Specialized error handler factory for retrofit with rx extension
 */

class RXErrorHandlingCallAdapterFactory
@Inject constructor(private val networkErrorCheckerParser: INetworkErrorCheckerParser,
                    private val schedulerProvider: ISchedulerProvider)
    : CallAdapter.Factory() {

    private var mOriginalCallAdapterFactory: RxJava2CallAdapterFactory

    init {
        mOriginalCallAdapterFactory = RxJava2CallAdapterFactory.createWithScheduler(
                schedulerProvider.io())
    }

    @Suppress("UNCHECKED_CAST", "NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    override fun get(returnType: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): CallAdapter<*, *>? {
        return RxCallAdapterWrapper(
                networkErrorCheckerParser,
                mOriginalCallAdapterFactory.get(returnType, annotations, retrofit) as CallAdapter<Any, *>,
                schedulerProvider)
    }

    private class RxCallAdapterWrapper<R : Any>(private val networkErrorCheckerParser: INetworkErrorCheckerParser,
                                                private val mWrappedCallAdapter: CallAdapter<R, *>,
                                                private val schedulerProvider: ISchedulerProvider) : CallAdapter<R, Observable<R>> {

        override fun responseType(): Type {
            return mWrappedCallAdapter.responseType()
        }

        @Suppress("UNCHECKED_CAST")
        override fun adapt(call: Call<R>): Observable<R> {
            val rx = (mWrappedCallAdapter.adapt(call) as Observable<R>)
            return rx
                    .retryWhen { errors ->
                        errors.flatMap { throwable ->
                            // Check the errors
                            when (throwable) {
                                is RetrofitException -> {
                                    // If it is our error -> check the kind
                                    when (throwable.kind) {
                                    // The case is a network error -> retry
                                        RetrofitException.Kind.NETWORK -> Observable.just(throwable)
                                        else -> {
                                            Logger.i("Non supported App retrofit error -> mark as a failure")
                                            Observable.error(throwable)
                                        }
                                    }
                                }
                                else -> {
                                    // Otherwise simply drop our error to the subscriber
                                    Observable.error(throwable)
                                }
                            }
                        }
                    }
                    .onErrorResumeNext { throwable: Throwable ->
                        // Main error handler
                        Observable.error(networkErrorCheckerParser.asRetrofitException(throwable))
                    }
                    .observeOn(schedulerProvider.ui())
        }
    }
}