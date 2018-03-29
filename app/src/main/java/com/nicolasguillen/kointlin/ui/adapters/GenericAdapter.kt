package com.nicolasguillen.kointlin.ui.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.SetCurrencyViewModelInputs
import com.nicolasguillen.kointlin.ui.viewholders.*

class GenericAdapter(private val list: List<Any>,
                     private val inputs: Any,
                     private val layout: Int): RecyclerView.Adapter<BaseViewHolder>() {

    constructor(list: List<Any>, layout: Int) : this(list, Any(), layout)

    override fun onCreateViewHolder(viewGroup: ViewGroup, layout: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(layout, viewGroup, false)
        return viewHolder(layout, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(objectFromPosition(position))
    }

    override fun getItemCount(): Int = list.size

    override fun getItemViewType(position: Int): Int {
        return layout(position)
    }

    @LayoutRes
    private fun layout(@Suppress("UNUSED_PARAMETER") position: Int): Int = layout

    private fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.item_set_currency ->
                CurrencyViewHolder(view, inputs as SetCurrencyViewModelInputs)
            R.layout.item_wallet ->
                AssetViewHolder(view)
            R.layout.item_news_feed ->
                NewsFeedViewHolder(view)
            else -> EmptyViewHolder(view)
        }
    }

    private fun objectFromPosition(position: Int): Any {
        return this.list[position]
    }
}