package com.nicolasguillen.kointlin.ui.viewholders

import android.view.View
import android.widget.TextView
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.SetCurrencyViewModelInputs
import com.nicolasguillen.kointlin.usecases.DisplayableCurrency


class CurrencyViewHolder(view: View,
                         val inputs: SetCurrencyViewModelInputs): BaseViewHolder(view) {

    override fun bindData(data: Any) {
        val displayableCurrency = data as DisplayableCurrency

        val value = view().findViewById<TextView>(R.id.item_set_currency_value)
        value.text = context().getString(R.string.settings_currency_disp)
                .format(displayableCurrency.displayName, displayableCurrency.currencyCode)

        view().setOnClickListener { inputs.didSelectCurrency(displayableCurrency) }
    }
}