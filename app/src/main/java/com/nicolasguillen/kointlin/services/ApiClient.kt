package com.nicolasguillen.kointlin.services

import com.google.gson.Gson
import com.nicolasguillen.kointlin.libs.rx.operators.ApiErrorOperator
import com.nicolasguillen.kointlin.libs.rx.operators.Operators
import com.nicolasguillen.kointlin.services.reponses.CoinPreview
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers

class ApiClient(private val service: ApiService, private val gson: Gson): ApiRepository {

    override fun getSupportedCoins(): Flowable<List<CoinPreview>> {
        return service
                .getSupportedCoins()
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