package com.tsulok.myreddit.ui.stories.mainpage.posts.adapter

import android.content.Context
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.tsulok.myreddit.R
import com.tsulok.myreddit.extensions.loadImage
import com.tsulok.myreddit.ui.base.list.viewholder.BaseViewHolder
import com.tsulok.myreddit.ui.stories.mainpage.posts.model.MainPagePostItem
import kotterknife.bindView
import java.text.DateFormat

/**
 * Viewholder for device listing items
 */
class MainPagePostsViewHolder(view: View, context: Context,
                              private val dateFormat: DateFormat) : BaseViewHolder<MainPagePostItem>(view, context) {

    private val titleTextView: TextView by bindView(R.id.postTitleTxt)
    private val authorTextView: TextView by bindView(R.id.postAuthorTxt)
    private val createdDateTextView: TextView by bindView(R.id.postDateTxt)
    private val postImageView: ImageView by bindView(R.id.postImg)

    override fun bind(data: MainPagePostItem) {
        titleTextView.text = Html.fromHtml(data.title)
        authorTextView.text = data.author
        createdDateTextView.text = dateFormat.format(data.createdDate)
        postImageView.loadImage(context, data.imageUrl)
    }
}