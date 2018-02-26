package com.tsulok.myreddit.extensions

import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso
import com.tsulok.myreddit.R

/**
 * Extensions for imageViews
 */
fun ImageView.loadImage(context: Context,
                        imageUrl: String?,
                        @DrawableRes placeHolder: Int = R.drawable.juho_salila_stormtrooper_render) {
    Picasso.with(context)
            .load(imageUrl ?: "")
            .placeholder(placeHolder)
            .fit()
            .centerCrop()
            .into(this)
}