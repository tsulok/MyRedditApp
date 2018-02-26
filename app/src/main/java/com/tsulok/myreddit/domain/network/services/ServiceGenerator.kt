package com.tsulok.myreddit.domain.network.services

import com.squareup.moshi.Moshi
import com.tsulok.myreddit.BuildConfig
import com.tsulok.myreddit.domain.network.utility.RXErrorHandlingCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Generator for services
 */
@Singleton
class ServiceGenerator
@Inject constructor(okHttpClient: OkHttpClient,
                    moshi: Moshi,
                    callAdapterFactory: RXErrorHandlingCallAdapterFactory) {

    private val retrofit: Retrofit

    init {

        val nullOnEmptyConverterFactory = object : Converter.Factory() {
            fun converterFactory() = this
            override fun responseBodyConverter(type: Type, annotations: Array<out Annotation>, retrofit: Retrofit) = object : Converter<ResponseBody, Any?> {
                val nextResponseBodyConverter = retrofit.nextResponseBodyConverter<Any?>(converterFactory(), type, annotations)
                override fun convert(value: ResponseBody) = if (value.contentLength() != 0L) nextResponseBodyConverter.convert(value) else null
            }
        }

        retrofit = Retrofit.Builder()
                .baseUrl(BuildConfig.SERVER_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(callAdapterFactory)
                .addConverterFactory(nullOnEmptyConverterFactory)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
    }

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }
}