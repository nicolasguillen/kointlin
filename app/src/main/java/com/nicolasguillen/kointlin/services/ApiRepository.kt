package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.CoinPreview
import io.reactivex.Flowable

interface ApiRepository {

    fun getSupportedCoins(): Flowable<List<CoinPreview>>
}
