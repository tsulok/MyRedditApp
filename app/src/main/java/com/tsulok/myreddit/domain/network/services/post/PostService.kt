package com.tsulok.myreddit.domain.network.services.post

import com.tsulok.myreddit.domain.network.model.BaseRootResponseDTO
import com.tsulok.myreddit.domain.network.services.post.dto.PostItemDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/*
* Post service operations
*/

interface PostService {

    companion object Constants {
        const val basePath: String = "{subreddit}"
    }

    @GET("$basePath/new.json")
    fun listNewPosts(@Path(value = "subreddit") subreddit: String): Observable<BaseRootResponseDTO<PostItemDto>>
}