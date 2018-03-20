package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.libs.util.addTo
import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.ui.adapters.AssetsAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import javax.inject.Inject

class AccountActivity: AppCompatActivity() {

    private val disposables = CompositeDisposable()

    @Inject lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_account)

        viewModel.outputs
                .assets()
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

    private fun showAssets(list: List<Asset>) {
        val assetList = findViewById<RecyclerView>(R.id.account_asset_list)
        assetList.adapter = AssetsAdapter(list)
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }

    private fun init() {
        setSupportActionBar(findViewById(R.id.account_toolbar))

        findViewById<FloatingActionButton>(R.id.account_add_new)
                .setOnClickListener { viewModel.inputs.didPressAdd() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ActivityRequestCodes.ADD_NEW_ASSET) {
            if (resultCode == RESULT_OK) {
                viewModel.inputs.viewDidLoad()
            }
        }
    }

    private fun <I> Observable<I>.crashingSubscribe(onNext: (I) -> Unit) {
        subscribe(onNext, { throw OnErrorNotImplementedException(it) }).addTo(disposables)
    }

}