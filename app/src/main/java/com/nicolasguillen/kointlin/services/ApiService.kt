package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.PriceDetail
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/rest/updateAllCoins_v3")
    fun getPriceDetailFromCoin(@Query("coin") coin: String,
                               @Query("base") base: String,
                               @Query("exchange") exchange: String): Flowable<Response<PriceDetail>>
}
