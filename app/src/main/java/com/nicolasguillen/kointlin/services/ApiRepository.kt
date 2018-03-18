package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single

interface ApiRepository {

    fun getTopCoins(): Single<List<TopCoin>>

    fun getCoinFromId(id: String): Single<List<TopCoin>>
}
