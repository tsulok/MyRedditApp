package com.tsulok.myreddit.domain.network.services.post.dto

import com.squareup.moshi.Json
import java.util.*

/**
 * DTOs for posts
 */

/**
 * Response for posts
 */
data class PostItemDto(val id: String?,
                       val author: String?,
                       val score: Int = 0,
                       val url: String?,
                       val thumbnail: String?,
                       val title: String?,
                       val preview: PostItemMediaRoot?,
                       @Json(name = "created_utc") val createdDate: Date)

data class PostItemMediaRoot(val images: List<PostItemMediaItemRoot>?,
                             val enabled: Boolean)

data class PostItemMediaItemRoot(val resolutions: List<PostItemMedia>)
data class PostItemMedia(val url: String?)