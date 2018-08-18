package com.nicolasguillen.kointlin.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.nicolasguillen.kointlin.KointlinApp
import com.nicolasguillen.kointlin.R
import com.nicolasguillen.kointlin.models.NewsFeedViewModel
import com.nicolasguillen.kointlin.ui.adapters.GenericAdapter
import com.nicolasguillen.kointlin.usecases.DisplayableFeed
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_news_feed.*

class NewsFeedActivity: BaseActivity<NewsFeedViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        KointlinApp.applicationComponent.inject(this)

        setContentView(R.layout.activity_news_feed)

        viewModel.outputs
                .newsFeed()
                .observeOn(AndroidSchedulers.mainThread())
                .crashingSubscribe { this.displayList(it) }

        init()

        viewModel.inputs.viewDidLoad()
    }

    private fun displayList(list: List<DisplayableFeed>) {
        newsFeedRecyclerView.layoutManager = LinearLayoutManager(this)
        newsFeedRecyclerView.adapter = GenericAdapter(list, R.layout.item_news_feed)
    }

    private fun init() {
        setSupportActionBar(newsFeedToolbar)
        supportActionBar?.setTitle(R.string.news_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}