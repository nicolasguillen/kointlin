package com.nicolasguillen.kointlin.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.squareup.picasso.Picasso
import kotterknife.bindView

class AssetViewHolder(view: View): BaseViewHolder(view) {

    private val icon: ImageView by bindView(R.id.item_asset_icon)
    private val name: TextView by bindView(R.id.item_asset_name)
    private val amount: TextView by bindView(R.id.item_asset_amount)

    override fun bindData(data: Any) {
        val asset = data as Asset

        Picasso.with(context())
                .load("https://coincap.io/images/coins/${asset.longName}.png")
                .into(icon)

        name.text = asset.longName
        amount.text = asset.amount.toString()

    }
}