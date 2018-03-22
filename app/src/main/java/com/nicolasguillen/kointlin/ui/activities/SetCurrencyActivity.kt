package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.SetCurrencyViewModel
import com.nicolasguillen.kointlin.ui.adapters.SetCurrencyAdapter
import com.nicolasguillen.kointlin.usecases.DisplayableCurrency
import io.reactivex.android.schedulers.AndroidSchedulers

class SetCurrencyActivity: BaseActivity<SetCurrencyViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_set_currency)

        viewModel.outputs
                .currencies()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.displayList(it) }

        viewModel.outputs
                .didSaveSettings()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe {
                    setResult(Activity.RESULT_OK)
                    finish()
                }

        init()

        viewModel.inputs.viewDidLoad()
    }

    private fun displayList(list: List<DisplayableCurrency>) {
        val recyclerView = findViewById<RecyclerView>(R.id.set_currency_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = SetCurrencyAdapter(list, viewModel.inputs)
    }

    private fun init() {
        supportActionBar?.setTitle(R.string.settings_currency_change)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}