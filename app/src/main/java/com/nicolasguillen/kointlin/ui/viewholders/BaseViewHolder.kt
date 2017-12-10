package com.nicolasguillen.kointlin.ui.viewholders

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View

abstract class BaseViewHolder(private val view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    override fun onClick(view: View) {}

    abstract fun bindData(data: Any)

    protected fun view(): View = view

    protected fun context(): Context = view.context
}
