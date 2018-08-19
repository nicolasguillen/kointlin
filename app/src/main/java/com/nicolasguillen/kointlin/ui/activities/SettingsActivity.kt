package com.nicolasguillen.kointlin.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.jakewharton.rxbinding2.view.RxView
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
                .appVersion()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.setAppVersionValue(it) }

        viewModel.outputs
                .defaultCurrency()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.setCurrencyValue(it) }

        RxView.clicks(settingsCurrency)
                .crashingSubscribe {
                    startActivityForResult(
                            Intent(this, SetCurrencyActivity::class.java),
                            ActivityRequestCodes.SET_CURRENCY
                    )
                }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun setAppVersionValue(appVersion: String) {
        settings_app_version.text = getString(R.string.settings_app_version).format(appVersion)
    }

    private fun setCurrencyValue(currency: String) {
        settings_currency_value.text = currency
    }

    private fun init() {
        setSupportActionBar(settingsToolbar)
        supportActionBar?.title = getString(R.string.settings_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
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