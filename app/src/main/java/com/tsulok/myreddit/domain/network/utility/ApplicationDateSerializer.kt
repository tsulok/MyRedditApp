package com.tsulok.myreddit.domain.network.utility

import android.util.JsonToken
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Date serializer for app
 */
@Singleton
class ApplicationDateSerializer
@Inject constructor() : JsonAdapter<Date>() {

    override fun fromJson(reader: JsonReader?): Date? {
        if (reader?.peek() === JsonToken.NULL) {
            return Date(0)
        }

        return deserializeToDate(reader?.nextLong())
    }

    override fun toJson(writer: JsonWriter?, value: Date?) {
        if (value == null) {
            writer?.nullValue()
            return
        }

        writer?.value(value.time)
    }

    @Synchronized
    private fun deserializeToDate(time: Long?): Date {

        if (time == null) {
            return Date(0)
        }

        return Date(time * 1000)
    }
}