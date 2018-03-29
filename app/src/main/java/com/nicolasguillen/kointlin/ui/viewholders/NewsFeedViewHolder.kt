package com.nicolasguillen.kointlin.ui.viewholders

import android.view.View
import android.widget.TextView
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.util.loadUrlIntoImage
import com.nicolasguillen.kointlin.usecases.DisplayableFeed
import com.squareup.picasso.Picasso


class NewsFeedViewHolder(view: View): BaseViewHolder(view) {

    override fun bindData(data: Any) {
        val displayableFeed = data as DisplayableFeed

        val title = view().findViewById<TextView>(R.id.news_feed_title)
        title.text = displayableFeed.coindeskFeed.title

        val date = view().findViewById<TextView>(R.id.news_feed_date)
        date.text = displayableFeed.coindeskFeed.datetime

        Picasso.with(context()).loadUrlIntoImage(
                view().findViewById(R.id.news_feed_image),
                displayableFeed.coindeskFeed.image
        )

//        view().setOnClickListener { inputs.didSelectCurrency(displayableCurrency) }
    }
}