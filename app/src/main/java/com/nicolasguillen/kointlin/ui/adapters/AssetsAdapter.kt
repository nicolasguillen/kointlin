package com.nicolasguillen.kointlin.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.ui.viewholders.BaseViewHolder
import android.support.annotation.LayoutRes
import android.view.View
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.ui.viewholders.AssetViewHolder
import android.view.LayoutInflater

class AssetsAdapter(private val assetList: List<Asset>): RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, layout: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(layout, viewGroup, false)
        return viewHolder(layout, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(objectFromPosition(position))
    }

    override fun getItemCount(): Int = assetList.size

    override fun getItemViewType(position: Int): Int {
        return layout(position)
    }

    @LayoutRes
    private fun layout(@Suppress("UNUSED_PARAMETER") position: Int): Int = R.layout.item_wallet

    private fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.item_wallet -> AssetViewHolder(view)
            else -> AssetViewHolder(view)
        }
    }

    private fun objectFromPosition(position: Int): Any {
        return this.assetList[position]
    }
}