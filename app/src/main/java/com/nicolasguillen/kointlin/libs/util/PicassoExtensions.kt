package com.nicolasguillen.kointlin.libs.util

import android.widget.ImageView
import com.nicolasguillen.kointlin.R
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

fun Picasso.loadIntoImage(imageView: ImageView, imageId: String){
    val context = imageView.context
    val urlPath = context.getString(R.string.image_downloader_url).replace("imageId", imageId)
    load(urlPath).networkPolicy(NetworkPolicy.OFFLINE)
            .into(imageView, object: Callback {
                override fun onSuccess() {}

                override fun onError(e: Exception) {
                    // Try again online if it failed
                    load(urlPath).into(imageView)
                }
            })
}

fun Picasso.loadUrlIntoImage(imageView: ImageView, urlPath: String){
    load(urlPath).networkPolicy(NetworkPolicy.OFFLINE)
            .into(imageView, object: Callback {
                override fun onSuccess() {}

                override fun onError(e: Exception) {
                    // Try again online if it failed
                    load(urlPath).into(imageView)
                }
            })
}