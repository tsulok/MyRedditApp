package com.tsulok.myreddit.domain.network.model

import io.reactivex.Observable

/**
 * Network error data wrapper
 */
data class NetworkError(val success: Boolean = false,
                        val message: String = "Unknown Error",
                        val code: Int = -1,
                        val error: String = "Unknown Error")

enum class EmptyResponse {
    Empty;

    fun observable(): Observable<EmptyResponse> {
        return Observable.just(this)
    }
}