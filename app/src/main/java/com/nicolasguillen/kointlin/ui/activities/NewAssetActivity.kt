package com.nicolasguillen.kointlin.ui.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.NewAssetViewModel
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import kotterknife.bindView
import javax.inject.Inject

class NewAssetActivity : RxAppCompatActivity() {

    private val spinner: Spinner by bindView(R.id.new_asset_selection)
    private val save: Button by bindView(R.id.new_asset_save)

    @Inject lateinit var viewModel: NewAssetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent
                .inject(this)

        setContentView(R.layout.activity_new_asset)

        viewModel.outputs
                .allSupportedCoins()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { spinner.adapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, it) }

        viewModel.outputs
                .didSave()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { finish() }

        viewModel.outputs
                .apiError()
                .bindToLifecycle(this)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { Toast.makeText(this, "Woops!", Toast.LENGTH_SHORT).show() }

        init()

        viewModel.inputs.viewDidLoad()

    }

    private fun init() {
        save.setOnClickListener { viewModel.inputs.didPressSave() }

    }

}