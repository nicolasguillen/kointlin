package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.models.SettingsViewModel
import io.reactivex.android.schedulers.AndroidSchedulers

class SettingsActivity: BaseActivity<SettingsViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_settings)

        init()

        viewModel.outputs
                .appVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.setAppVersionValue(it) }

        viewModel.outputs
                .defaultCurrency()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.setCurrencyValue(it) }

        viewModel.inputs.viewDidLoad()
    }

    private fun setAppVersionValue(appVersion: String) {
        findViewById<TextView>(R.id.settings_app_version)
                .text = getString(R.string.settings_app_version).format(appVersion)
    }

    private fun setCurrencyValue(currency: String) {
        findViewById<TextView>(R.id.settings_currency_value)
                .text = currency
    }

    private fun init() {
        val toolbar = findViewById<Toolbar>(R.id.AppBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.settings_title)
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