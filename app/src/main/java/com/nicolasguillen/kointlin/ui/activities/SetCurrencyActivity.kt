package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.SetCurrencyViewModel
import com.nicolasguillen.kointlin.ui.adapters.GenericAdapter
import com.nicolasguillen.kointlin.usecases.DisplayableCurrency
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_set_currency.*

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
        setCurrencyRecyclerView.layoutManager = LinearLayoutManager(this)
        setCurrencyRecyclerView.adapter = GenericAdapter(list, viewModel.inputs, R.layout.item_set_currency)
    }

    private fun init() {
        setSupportActionBar(setCurrencyToolbar)
        supportActionBar?.title = getString(R.string.settings_currency_change)
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