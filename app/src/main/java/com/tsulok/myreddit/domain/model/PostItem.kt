package com.tsulok.myreddit.domain.model

import com.tsulok.myreddit.domain.repository.database.models.PostItemDBModel
import java.util.*

/**
 * BL Post item model
 */
data class PostItem(val id: String,
                    val author: String,
                    val date: Date,
                    val title: String,
                    val url: String?,
                    val thumbnail: String?) {
    constructor(model: PostItemDBModel) : this(
            model.id,
            model.authorName,
            model.postDate,
            model.title,
            model.contentUrl,
            model.thumbnailUrl) {

    }
}