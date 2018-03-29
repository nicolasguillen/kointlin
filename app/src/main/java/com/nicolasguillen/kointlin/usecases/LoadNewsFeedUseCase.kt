package com.nicolasguillen.kointlin.usecases

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.errors.ApiException
import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import io.reactivex.Single

interface LoadNewsFeedUseCase {

    fun fetchNewsFeed(): Single<FetchNewsFeedResult>
}

class LoadNewsFeedUseCaseImpl(private val apiRepository: ApiRepository) : LoadNewsFeedUseCase {

    override fun fetchNewsFeed(): Single<FetchNewsFeedResult> {
        return Single.create { observer ->
            this.apiRepository.getCoindeskNewsFeed()
                    .map { it.map { DisplayableFeed(it) } }
                    .subscribe(
                            { observer.onSuccess(FetchNewsFeedResult.Success(it)) },
                            { error -> when(error){
                                is ApiException ->
                                    observer.onSuccess(FetchNewsFeedResult.ApiError(error.errorEnvelope.status))
                                else ->
                                    observer.onSuccess(FetchNewsFeedResult.UnknownError)
                            } }
                    )
        }
    }
}

class DisplayableFeed(val coindeskFeed: CoindeskFeed)

sealed class FetchNewsFeedResult {
    class Success(val newsFeedList: List<DisplayableFeed>): FetchNewsFeedResult()
    class ApiError(val errorCode: Int?): FetchNewsFeedResult()
    object UnknownError: FetchNewsFeedResult()
}
