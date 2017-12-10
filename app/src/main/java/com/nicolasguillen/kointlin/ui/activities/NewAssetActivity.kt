package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.Button
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotterknife.bindView
import javax.inject.Inject

class NewAssetActivity : RxAppCompatActivity() {

    private val save: Button by bindView(R.id.new_asset_save)
    private val amountInput: TextInputEditText by bindView(R.id.new_asset_amount)

    @Inject lateinit var viewModel: NewAssetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_new_asset)

        viewModel.outputs
                .didSave()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { this.reportOkAndFinish() }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun init() {
        supportActionBar?.title = "Add new asset"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        save.setOnClickListener { viewModel.inputs.didPressSave() }
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(editable: Editable?) = viewModel.inputs.didEnterAmount(editable.toString())
        })
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