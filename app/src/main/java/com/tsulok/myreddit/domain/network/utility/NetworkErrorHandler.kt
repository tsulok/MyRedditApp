package com.tsulok.myreddit.domain.network.utility

import com.tsulok.myreddit.domain.network.model.NetworkError
import com.tsulok.myreddit.domain.network.model.RetrofitException


/**
 * Handle the common error messages from the network
 */

class NetworkErrorHandler {

    fun handleNetworkError(throwable: Throwable, errorListener: INetworkErrorListener) {

        if (throwable is RetrofitException) {

            when (throwable.kind) {
                RetrofitException.Kind.NETWORK -> errorListener.onNetworkErrorOccurred()
                RetrofitException.Kind.HTTP -> {
                    throwable.networkError?.
                            let { errorListener.onHttpErrorOccurred(throwable.networkError) } ?:
                            run { errorListener.onHttpErrorOccurred() }
                }
                else -> errorListener.onErrorNotSupported()
            }
        }
    }

    interface INetworkErrorListener {
        fun onNetworkErrorOccurred()

        fun onHttpErrorOccurred(networkError: NetworkError?)

        fun onHttpErrorOccurred()

        fun onErrorNotSupported()
    }
}