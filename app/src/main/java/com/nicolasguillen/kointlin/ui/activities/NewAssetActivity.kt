package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView.clicks
import com.jakewharton.rxbinding2.widget.RxAutoCompleteTextView.itemClickEvents
import com.jakewharton.rxbinding2.widget.RxTextView.afterTextChangeEvents
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import kotlinx.android.synthetic.main.activity_new_asset.*

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
                    newAssetSaveButton.isEnabled = isEnabled
                }

        viewModel.outputs
                .didSave()
                .observeOn(mainThread())
                .crashingSubscribe { this.reportOkAndFinish() }

        itemClickEvents(newAssetInput)
                .crashingSubscribe { data -> viewModel.inputs.didSelectCoin((data.clickedView() as TextView).text.toString()) }

        afterTextChangeEvents(newAssetAmount)
                .crashingSubscribe { data -> viewModel.inputs.didEnterAmount(data.editable().toString()) }

        clicks(newAssetSaveButton)
                .crashingSubscribe { viewModel.inputs.didPressSave() }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun init() {
        setSupportActionBar(newAssetToolbar)
        supportActionBar?.title = "Add new asset"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setCoinsToAdapter(coins: List<TopCoin>){
        newAssetInput.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coins.map { it.name }))
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