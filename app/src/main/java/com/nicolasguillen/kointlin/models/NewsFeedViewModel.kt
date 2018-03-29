package com.nicolasguillen.kointlin.models

import com.nicolasguillen.kointlin.usecases.DisplayableFeed
import com.nicolasguillen.kointlin.usecases.FetchNewsFeedResult
import com.nicolasguillen.kointlin.usecases.LoadNewsFeedUseCase
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

interface NewsFeedViewModelInputs {
    fun viewDidLoad()
}

interface NewsFeedViewModelOutputs {
    fun newsFeed(): Observable<List<DisplayableFeed>>
}

class NewsFeedViewModel(private val useCase: LoadNewsFeedUseCase): NewsFeedViewModelInputs, NewsFeedViewModelOutputs {

    //INPUTS
    private val viewDidLoad = PublishSubject.create<Unit>()

    //OUTPUTS
    private val newsFeed = PublishSubject.create<List<DisplayableFeed>>()
    override fun newsFeed(): Observable<List<DisplayableFeed>> = newsFeed

    val inputs: NewsFeedViewModelInputs = this
    val outputs: NewsFeedViewModelOutputs = this

    init {

        viewDidLoad
                .switchMapSingle { this.useCase.fetchNewsFeed() }
                .subscribe { when(it) {
                    is FetchNewsFeedResult.Success ->
                        newsFeed.onNext(it.newsFeedList)
                } }
    }

    override fun viewDidLoad() = viewDidLoad.onNext(Unit)

}