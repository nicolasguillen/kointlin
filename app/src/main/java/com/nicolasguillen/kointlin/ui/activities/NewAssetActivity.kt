package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.*
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

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun init() {
        supportActionBar?.title = "Add new asset"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val save = findViewById<View>(R.id.new_asset_save)
        save.setOnClickListener { viewModel.inputs.didPressSave() }

        val amountInput = findViewById<TextInputEditText>(R.id.new_asset_amount)
        amountInput.afterTextChanged { viewModel.inputs.didEnterAmount(it) }

    }

    private fun setCoinsToAdapter(coins: List<TopCoin>){
        val input = findViewById<AutoCompleteTextView>(R.id.new_asset_input)
        input.setAdapter(ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, coins.map { it.name }))
        input.setOnItemClickListener { _, view, _, _ -> viewModel.inputs.didSelectCoin((view as TextView).text.toString()) }
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

    private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(editable: Editable?) = afterTextChanged.invoke(editable.toString())
        })
    }
}