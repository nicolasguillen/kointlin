package com.nicolasguillen.kointlin.ui.viewholders

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.util.loadIntoImage
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.squareup.picasso.Picasso

class AssetViewHolder(view: View): BaseViewHolder(view) {

    override fun bindData(data: Any) {
        val asset = data as Asset

        val icon = view().findViewById<ImageView>(R.id.item_asset_icon)
        Picasso.with(context())
                .loadIntoImage(icon, asset.longName)

        val name = view().findViewById<TextView>(R.id.item_asset_name)
        name.text = asset.longName

        val amount = view().findViewById<TextView>(R.id.item_asset_amount)
        amount.text = asset.amount.toString()

    }
}