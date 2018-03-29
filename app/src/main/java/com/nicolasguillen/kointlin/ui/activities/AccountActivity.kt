package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.ui.adapters.GenericAdapter
import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import io.reactivex.android.schedulers.AndroidSchedulers

class AccountActivity: BaseActivity<AccountViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_account)

        viewModel.outputs
                .displayableAssets()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { showAssets(it) }

        viewModel.outputs
                .totalAmount()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { updateTitle(it) }

        viewModel.outputs
                .startNewAssetActivity()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { startAddNewAsset() }

        viewModel.inputs.viewDidLoad()

        init()
    }

    private fun showAssets(list: List<DisplayableAsset>) {
        val assetList = findViewById<RecyclerView>(R.id.account_asset_list)
        assetList.adapter = GenericAdapter(list, R.layout.item_wallet)
        assetList.layoutManager = LinearLayoutManager(this)
    }

    private fun updateTitle(totalAmount: Double) {
        findViewById<Toolbar>(R.id.account_toolbar)
                .title = totalAmount.toString()
        findViewById<CollapsingToolbarLayout>(R.id.account_collapsing_toolbar)
                .title = totalAmount.toString()
    }

    private fun startAddNewAsset() {
        startActivityForResult(Intent(this, NewAssetActivity::class.java), ActivityRequestCodes.ADD_NEW_ASSET)
    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.account_toolbar))

        findViewById<FloatingActionButton>(R.id.account_add_new)
                .setOnClickListener { viewModel.inputs.didPressAdd() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            ActivityRequestCodes.ADD_NEW_ASSET -> {
                if (resultCode == RESULT_OK) {
                    viewModel.inputs.viewDidLoad()
                }
            }
            ActivityRequestCodes.SETTINGS -> {
                if (resultCode == RESULT_OK) {
                    viewModel.inputs.viewDidLoad()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_account, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.account_settings ->
                    startActivityForResult(
                            Intent(this, SettingsActivity::class.java),
                            ActivityRequestCodes.SETTINGS
                    )
        }
        return true
    }

}