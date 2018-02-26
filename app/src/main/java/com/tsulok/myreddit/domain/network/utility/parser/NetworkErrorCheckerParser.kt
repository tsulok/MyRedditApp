package com.tsulok.myreddit.domain.network.utility.parser

import com.orhanobut.logger.Logger
import com.squareup.moshi.Moshi
import com.tsulok.myreddit.domain.network.model.NetworkError
import com.tsulok.myreddit.domain.network.model.RetrofitException
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

/**
 * Parse a throwable into a retrofit error
 */
interface INetworkErrorCheckerParser {
    fun asRetrofitException(throwable: Throwable): RetrofitException
    fun isResponseAuthenticationNeeded(response: Response): Boolean
}

/**
 * Network error parser
 */

class NetworkErrorCheckerParser
@Inject constructor(private val moshi: Moshi) : INetworkErrorCheckerParser {

    override fun asRetrofitException(throwable: Throwable): RetrofitException {

        // We are already have this error
        if (throwable is RetrofitException) {
            return throwable
        }

        // We had non-200 http error
        if (throwable is HttpException) {
            val response = throwable.response()
            val networkError = parseNetworkErrorIfPossible(response.errorBody())

            if (isReauthenticationNeeded(networkError)) {
                return RetrofitException.authenticationErrror(response)
            } else {
                networkError?.let {
                    return RetrofitException.httpError(response,
                            it)
                }
            }

            return RetrofitException.unexpectedError(throwable)
        }

        // A network error happened
        return if (throwable is IOException) {
            RetrofitException.networkError(throwable)
        } else RetrofitException.unexpectedError(throwable)
        // We don't know what happened. We need to simply convert to an unknown error
    }

    override fun isResponseAuthenticationNeeded(response: Response): Boolean {
        return response.code() == HttpURLConnection.HTTP_UNAUTHORIZED || isReauthenticationNeeded(parseNetworkErrorIfPossible(response))
    }

    private fun parseNetworkErrorIfPossible(response: Response): NetworkError? {
        val jsonAdapter = moshi.adapter<NetworkError>(NetworkError::class.java)
        return try {
            val json = response.peekBody(java.lang.Long.MAX_VALUE).string()
            if (json.isEmpty()) {
                return null
            }
            jsonAdapter.fromJson(json)
        } catch (exception: Exception) {
            Logger.e("Unable to parse network error, $exception")
            null
        }
    }

    private fun parseNetworkErrorIfPossible(errorBody: ResponseBody?): NetworkError? {
        val errorBody = errorBody?.let { it } ?: return null
        val jsonAdapter = moshi.adapter<NetworkError>(NetworkError::class.java)
        return try {
            val json = errorBody.string()
            jsonAdapter.fromJson(json)
        } catch (exception: Exception) {
            Logger.e("Unable to parse network error, $exception")
            null
        }
    }

    private fun isReauthenticationNeeded(networkError: NetworkError?): Boolean {
        networkError?.let {
            return !networkError.success && networkError.code == HttpURLConnection.HTTP_UNAUTHORIZED
        }

        return false
    }
}