package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.PriceDetail
import io.reactivex.Flowable

interface ApiRepository {

    fun getPriceDetailFromCoin(coin: String): Flowable<PriceDetail>
}
