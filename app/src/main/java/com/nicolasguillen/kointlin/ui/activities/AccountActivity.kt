package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.view.RxView
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.ui.adapters.GenericAdapter
import com.nicolasguillen.kointlin.ui.views.DisplayableAsset
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_account.*

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

        RxView.clicks(account_add_new)
                .crashingSubscribe { viewModel.inputs.didPressAdd() }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun showAssets(list: List<DisplayableAsset>) {
        accountAssetRecyclerView.adapter = GenericAdapter(list, R.layout.item_wallet)
        accountAssetRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun updateTitle(totalAmount: Double) {
        accountToolbar.title = totalAmount.toString()
        accountCollapsingToolbar.title = totalAmount.toString()
    }

    private fun startAddNewAsset() {
        startActivityForResult(Intent(this, NewAssetActivity::class.java), ActivityRequestCodes.ADD_NEW_ASSET)
    }

    private fun setRefreshingState(isLoading: Boolean) {
        accountRefreshLayout.isRefreshing = isLoading
    }

    private fun init() {
        setSupportActionBar(accountToolbar)
        accountRefreshLayout.setOnRefreshListener { viewModel.inputs.viewDidLoad() }
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.account_settings ->
                    startActivityForResult(Intent(this, SettingsActivity::class.java), ActivityRequestCodes.SETTINGS)
        }
        return true
    }

}