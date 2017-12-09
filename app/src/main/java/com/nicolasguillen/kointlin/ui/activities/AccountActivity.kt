package com.nicolasguillen.kointlin.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.TextView
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.AccountViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotterknife.bindView
import javax.inject.Inject

class AccountActivity : RxAppCompatActivity() {

    private val amount: TextView by bindView(R.id.account_amount)
    private val addNew: FloatingActionButton by bindView(R.id.account_add_new)

    @Inject lateinit var viewModel: AccountViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent
                .inject(this)

        setContentView(R.layout.activity_account)

        addNew.setOnClickListener { startActivity(Intent(this, NewAssetActivity::class.java)) }

        viewModel.outputs
                .totalAmount()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { amount.text = it.toString() }

        viewModel.inputs.viewDidLoad()

    }

}