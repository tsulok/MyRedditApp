package com.tsulok.myreddit.ui.stories.mainpage.posts.model

import com.tsulok.myreddit.R
import com.tsulok.myreddit.domain.model.PostItem
import com.tsulok.myreddit.ui.base.model.IBaseListType
import java.util.*

/**
 * UI model for posts
 */
data class MainPagePostItem(
        val id: String,
        val author: String,
        val title: String,
        val createdDate: Date,
        val imageUrl: String?) : IBaseListType {

    constructor(model: PostItem) : this(model.id, model.author, model.title, model.date, model.thumbnail)

    override val layoutType: Int = R.layout.main_page_list_item
}