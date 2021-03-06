package com.nicolasguillen.kointlin.services

import com.google.gson.Gson
import com.nicolasguillen.kointlin.libs.rx.operators.ApiErrorOperator
import com.nicolasguillen.kointlin.libs.rx.operators.Operators
import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class ApiClient(private val apiService: ApiService,
                private val gson: Gson): ApiRepository {

    override fun getTopCoins(currency: String): Single<List<TopCoin>> {
        return apiService
                .getTopCoins(currency)
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io())
    }

    override fun getCoinFromId(id: String, currency: String): Single<List<TopCoin>> {
        return apiService
                .getCoinFromId(id, currency)
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io())
    }

    override fun getCoindeskNewsFeed(): Single<List<CoindeskFeed>> {
        return apiService
                .getCoindeskNewsFeed()
                .lift(apiErrorOperator())
                .subscribeOn(Schedulers.io())
    }

    /**
     * Utility to create a new [ApiErrorOperator], saves us from littering references to gson throughout the client.
     */
    private fun <T> apiErrorOperator(): ApiErrorOperator<T> {
        return Operators.Companion.apiError(gson)
    }
}