package com.tsulok.myreddit.domain.network.model

import android.support.annotation.Nullable
import retrofit2.Response
import java.io.IOException

/**
 * Exception wrapper for retrofit
 */

class RetrofitException : RuntimeException {

    /**
     * The request URL which produced the error.
     */
    val url: String?

    /**
     * Response object containing status code, headers, body, etc.
     */
    val response: Response<*>?

    /**
     * The event kind which triggered this error.
     */
    val kind: Kind

    /**
     * The network error provided by the server if available
     */
    val networkError: NetworkError?

    /**
     * Identifies the event kind which triggered a [RetrofitException].
     */
    enum class Kind {
        /**
         * An [IOException] occurred while communicating to the server.
         */
        NETWORK,
        /**
         * A non-200 HTTP status code was received from the server.
         */
        HTTP,
        /**
         * An internal error occurred while attempting to execute a request. It is best practice to
         * re-throw this exception so your application crashes.
         */
        UNEXPECTED,
        /**
         * Authentication neededed for the request
         */
        AUTH_NEEDED
    }

    private constructor(message: String, @Nullable url: String, @Nullable networkError: NetworkError,
                        @Nullable response: Response<*>, kind: Kind, exception: Throwable) : super(message, exception) {
        this.url = url
        this.response = response
        this.kind = kind
        this.networkError = networkError
    }

    constructor(message: String, url: String, kind: Kind, networkError: NetworkError?) : super(message) {
        this.url = url
        this.response = null
        this.kind = kind
        this.networkError = networkError
    }

    private constructor(message: String, kind: Kind, exception: Throwable) : super(message, exception) {
        this.url = null
        this.response = null
        this.kind = kind
        this.networkError = null
    }

    companion object {
        fun httpError(response: Response<*>, networkError: NetworkError): RetrofitException {
            val message = "${response.code()} ${response.message()}"
            return RetrofitException(message,
                    response.raw().request().url().toString(),
                    networkError, response, Kind.HTTP, UnknownRetrofitException())
        }

        fun httpError(url: String, networkError: NetworkError?, response: okhttp3.Response): RetrofitException {
            var message = response.code().toString() + " " + response.message()
            networkError?.let {
                message += " - " + networkError.message
            }

            return RetrofitException(message, url, Kind.HTTP, networkError)
        }

        fun authenticationErrror(response: Response<*>): RetrofitException {
            val message = "${response.code()} ${response.message()}"
            return RetrofitException(message,
                    Kind.AUTH_NEEDED,
                    AuthorizationExpiredException())
        }

        fun networkError(exception: IOException): RetrofitException {
            return RetrofitException(exception.message ?: "Unknown", Kind.NETWORK, exception)
        }

        fun unexpectedError(exception: Throwable): RetrofitException {
            return RetrofitException(exception.message ?: "Unknown", Kind.UNEXPECTED, exception)
        }
    }
}

class UnknownRetrofitException : RuntimeException("Unknown Retrofit Exception")
class AuthorizationExpiredException : RuntimeException("Re authentication needed")