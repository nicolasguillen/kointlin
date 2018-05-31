package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jakewharton.rxbinding2.view.clicks
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

        viewModel.outputs
                .isLoading()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { setRefreshingState(it) }

        findViewById<FloatingActionButton>(R.id.account_add_new)
                .clicks()
                .crashingSubscribe { viewModel.inputs.didPressAdd() }

        init()

        viewModel.inputs.viewDidLoad()

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

    private fun setRefreshingState(isLoading: Boolean) {
        findViewById<SwipeRefreshLayout>(R.id.account_refresh_asset_list).isRefreshing = isLoading
    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.account_toolbar))

        findViewById<SwipeRefreshLayout>(R.id.account_refresh_asset_list)
                .setOnRefreshListener { viewModel.inputs.viewDidLoad() }
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