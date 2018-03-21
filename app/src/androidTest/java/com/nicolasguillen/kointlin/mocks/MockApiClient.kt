package com.nicolasguillen.kointlin.mocks

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single

class MockApiClient: ApiRepository {

    override fun getTopCoins(): Single<List<TopCoin>> {
        return Single.just(listOf(
                TopCoin("BTC", "Bitcoin", "BTC", "6000.0"),
                TopCoin("ETH", "Etherium", "ETH", "200.0")
        ))
    }

    override fun getCoinFromId(id: String): Single<List<TopCoin>> {
        return Single.just(listOf(
                when(id) {
                    "BTC" -> TopCoin("BTC", "Bitcoin", "BTC", "6000.0")
                    "ETH" -> TopCoin("ETH", "Etherium", "ETH", "200.0")
                    else -> TopCoin("BTC", "Bitcoin", "BTC", "6000.0")
                }
        ))
    }


}