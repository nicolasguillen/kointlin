package com.nicolasguillen.kointlin.ui.adapters

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.SetCurrencyViewModelInputs
import com.nicolasguillen.kointlin.ui.viewholders.BaseViewHolder
import com.nicolasguillen.kointlin.ui.viewholders.CurrencyViewHolder
import com.nicolasguillen.kointlin.usecases.DisplayableCurrency

class SetCurrencyAdapter(private val currencyList: List<DisplayableCurrency>, val inputs: SetCurrencyViewModelInputs): RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, layout: Int): BaseViewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val view = layoutInflater.inflate(layout, viewGroup, false)
        return viewHolder(layout, view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bindData(objectFromPosition(position))
    }

    override fun getItemCount(): Int = currencyList.size

    override fun getItemViewType(position: Int): Int {
        return layout(position)
    }

    @LayoutRes
    private fun layout(@Suppress("UNUSED_PARAMETER") position: Int): Int = R.layout.item_set_currency

    private fun viewHolder(@LayoutRes layout: Int, view: View): BaseViewHolder {
        return when (layout) {
            R.layout.item_set_currency -> CurrencyViewHolder(view, inputs)
            else -> CurrencyViewHolder(view, inputs)
        }
    }

    private fun objectFromPosition(position: Int): Any {
        return this.currencyList[position]
    }
}