package com.tsulok.myreddit.domain.repository.database.models

import com.tsulok.myreddit.domain.repository.base.BaseDAOMapper
import com.tsulok.myreddit.domain.repository.base.IBaseDAOMapper
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

/**
 * DB Model for users
 */
open class PostItemDBModel(
        @PrimaryKey open var id: String = "",

        // Main metadata
        open var title: String = "",
        open var authorName: String = "",
        open var postDate: Date = Date(0),

        // Content
        open var contentUrl: String? = null,
        open var thumbnailUrl: String? = null) : RealmObject() {

    companion object Properties : BaseDAOMapper(), IBaseDAOMapper {
        val cNameTitle: String = "title"
        val cNameAuthorName: String = "authorName"
        val cNamePostDate: String = "postDate"
        val cNameContentUrl: String = "contentUrl"
        val cNameThumbnailUrl: String = "thumbnailUrl"
        val cNameClass: String = "PostItemDBModel"

        override fun provideBaseDAOMapper(): BaseDAOMapper {
            return this
        }
    }
}