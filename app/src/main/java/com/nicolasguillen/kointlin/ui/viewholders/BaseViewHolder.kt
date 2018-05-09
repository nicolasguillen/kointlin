package com.nicolasguillen.kointlin.ui.viewholders

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindData(data: Any)

    protected fun view(): View = view

    protected fun context(): Context = view.context
}
