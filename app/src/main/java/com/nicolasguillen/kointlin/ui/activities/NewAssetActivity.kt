package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.google.android.material.textfield.TextInputEditText
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import com.jakewharton.rxbinding2.widget.itemClickEvents
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread

class NewAssetActivity: BaseActivity<NewAssetViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_new_asset)

        viewModel.outputs
                .allCoins()
                .observeOn(mainThread())
                .crashingSubscribe { this.setCoinsToAdapter(it) }

        viewModel.outputs
                .isFormValid()
                .observeOn(mainThread())
                .crashingSubscribe { isEnabled ->
                    findViewById<Button>(R.id.new_asset_save).isEnabled = isEnabled
                }

        viewModel.outputs
                .didSave()
                .observeOn(mainThread())
                .crashingSubscribe { this.reportOkAndFinish() }

        findViewById<TextInputEditText>(R.id.new_asset_amount)
                .afterTextChangeEvents()
                .crashingSubscribe { data -> viewModel.inputs.didEnterAmount(data.editable().toString()) }

        findViewById<View>(R.id.new_asset_save)
                .clicks()
                .crashingSubscribe { viewModel.inputs.didPressSave() }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun init() {
        val toolbar = findViewById<Toolbar>(R.id.AppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Add new asset"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setCoinsToAdapter(coins: List<TopCoin>){
        val input = findViewById<AutoCompleteTextView>(R.id.new_asset_input)
        input.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coins.map { it.name }))
        input.itemClickEvents()
                .crashingSubscribe { data -> viewModel.inputs.didSelectCoin((data.clickedView() as TextView).text.toString()) }
    }

    private fun reportOkAndFinish(){
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}