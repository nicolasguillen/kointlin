package com.nicolasguillen.kointlin.mocks

import com.nicolasguillen.kointlin.services.ApiRepository
import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
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

    override fun getCoindeskNewsFeed(): Single<List<CoindeskFeed>> {
        return Single.just(listOf(
                CoindeskFeed("Enterprises Building Blockchain Confront Tech Limitations", "url", "url", "https://media.coindesk.com/uploads/2018/03/20180322_171743-copy-206x115.jpg", "Mar 23, 2018 at 11:40"),
                CoindeskFeed("Japan Warns Binance Exchange Over Licensing", "url", "url", "https://media.coindesk.com/uploads/2018/03/japanese-yen-201x134.jpg", "Mar 23, 2018 at 10:40"),
                CoindeskFeed("Blockchain Standardization Tops Chinese IT Ministry's 2018 Agenda", "url", "url", "https://media.coindesk.com/uploads/2018/03/china-flag-1-201x134.jpg", "Mar 23, 2018 at 10:00"),
                CoindeskFeed("Blockchain Remittances Face Efficiency Hurdle, Says Taiwan Central Bank", "url", "url", "https://media.coindesk.com/uploads/2018/03/Taiwan-dollar-1-180x134.jpg", "Mar 23, 2018 at 09:00"),
                CoindeskFeed("Coinbase In Talks to Buy Bitcoin Startup Earn.com", "url", "url", "https://media.coindesk.com/uploads/2018/03/Screen-Shot-2018-03-22-at-10.41.48-PM-206x134.png", "Mar 23, 2018 at 09:00")
        ))
    }

}