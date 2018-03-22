package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.models.SettingsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity: BaseActivity<SettingsViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_settings)

        viewModel.outputs
                .defaultCurrency()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.setCurrencyValue(it) }

        init()

        viewModel.inputs.viewDidLoad()
    }

    private fun setCurrencyValue(currency: String) {
        settingsCurrencyValueTextView.text = currency
    }

    private fun init() {
        supportActionBar?.setTitle(R.string.settings_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        findViewById<View>(R.id.settings_currency).setOnClickListener {
            startActivityForResult(
                    Intent(this, SetCurrencyActivity::class.java),
                    ActivityRequestCodes.SET_CURRENCY
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ActivityRequestCodes.SET_CURRENCY -> {
                setResult(Activity.RESULT_OK)
                viewModel.inputs.viewDidLoad()
            }
        }
    }
}