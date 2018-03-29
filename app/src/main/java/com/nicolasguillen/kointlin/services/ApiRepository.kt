package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single

interface ApiRepository {

    fun getTopCoins(currency: String): Single<List<TopCoin>>

    fun getCoinFromId(id: String, currency: String): Single<List<TopCoin>>

    fun getCoindeskNewsFeed(): Single<List<CoindeskFeed>>

}
