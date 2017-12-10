package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.libs.ActivityRequestCodes
import com.nicolasguillen.kointlin.models.AccountViewModel
import com.nicolasguillen.kointlin.storage.entities.Asset
import com.nicolasguillen.kointlin.ui.adapters.AssetsAdapter
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotterknife.bindView
import javax.inject.Inject

class AccountActivity : RxAppCompatActivity() {

    private val collapsingToolbar: CollapsingToolbarLayout by bindView(R.id.account_collapsing_toolbar)
    private val toolbar: Toolbar by bindView(R.id.account_toolbar)
    private val addNew by bindView<FloatingActionButton>(R.id.account_add_new)
    private val assetList: RecyclerView by bindView(R.id.account_asset_list)

    @Inject lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_account)

        viewModel.outputs
                .assets()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { showAssets(it) }

        viewModel.outputs
                .totalAmount()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { updateTitle(it) }

        viewModel.outputs
                .startNewAssetActivity()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { startActivityForResult(Intent(this, NewAssetActivity::class.java), ActivityRequestCodes.ADD_NEW_ASSET) }

        viewModel.inputs.viewDidLoad()

        init()
    }

    private fun showAssets(list: List<Asset>) {
        assetList.adapter = AssetsAdapter(list)
        assetList.layoutManager = LinearLayoutManager(this)
    }

    private fun updateTitle(totalAmount: Double) {
        toolbar.title = totalAmount.toString()
        collapsingToolbar.title = totalAmount.toString()
    }

    private fun init() {
        setSupportActionBar(toolbar)
        addNew.setOnClickListener { viewModel.inputs.didPressAdd() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == ActivityRequestCodes.ADD_NEW_ASSET) {
            if (resultCode == RESULT_OK) {
                viewModel.inputs.viewDidLoad()
            }
        }
    }

}