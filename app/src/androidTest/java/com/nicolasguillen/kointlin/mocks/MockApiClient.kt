package com.nicolasguillen.kointlin.mocks

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single

class MockApiClient: ApiRepository {

    override fun getTopCoins(currency: String): Single<List<TopCoin>> {
        return Single.just(listOf(
                TopCoin("BTC", "Bitcoin", "BTC", "6000.0", "0.621"),
                TopCoin("ETH", "Ethereum", "ETH", "200.0", "-1.45")
        ))
    }

    override fun getCoinFromId(id: String, currency: String): Single<List<TopCoin>> {
        return Single.just(listOf(
                when(id) {
                    "BTC" -> TopCoin("BTC", "Bitcoin", "BTC", "6000.0", "0.621")
                    "ETH" -> TopCoin("ETH", "Ethereum", "ETH", "200.0", "-1.45")
                    else -> TopCoin("BTC", "Bitcoin", "BTC", "6000.0", "0.621")
                }
        ))
    }


}