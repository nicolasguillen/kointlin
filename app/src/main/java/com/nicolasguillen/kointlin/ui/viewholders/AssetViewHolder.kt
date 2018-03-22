package com.nicolasguillen.kointlin.ui.viewholders

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.util.loadIntoImage
import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import com.squareup.picasso.Picasso
import java.text.NumberFormat
import java.util.*

class AssetViewHolder(view: View): BaseViewHolder(view) {

    override fun bindData(data: Any) {
        val displayableAsset = data as DisplayableAsset

        val numberFormat = NumberFormat.getCurrencyInstance()
        numberFormat.currency = Currency.getInstance(displayableAsset.currencyCode)

        val icon = view().findViewById<ImageView>(R.id.item_asset_icon)
        Picasso.with(context())
                .loadIntoImage(icon, displayableAsset.asset.longName)

        val name = view().findViewById<TextView>(R.id.item_asset_name)
        name.text = displayableAsset.asset.longName

        val amount = view().findViewById<TextView>(R.id.item_asset_amount)
        amount.text = context().getString(R.string.portfolio_amount)
                .format(displayableAsset.asset.amount.toString(), displayableAsset.asset.shortName)

        val change = view().findViewById<TextView>(R.id.item_asset_change)
        if(displayableAsset.variant > 0) {
            change.setTextColor(Color.GREEN)
            change.text = context().getString(R.string.portfolio_change)
                    .format("▲", numberFormat.format(displayableAsset.variant))
        } else {
            change.setTextColor(Color.RED)
            change.text = context().getString(R.string.portfolio_change)
                    .format("▼", numberFormat.format(displayableAsset.variant))
        }

        val total = view().findViewById<TextView>(R.id.item_asset_total)
        total.text = numberFormat.format(displayableAsset.currentPrice)
    }
}