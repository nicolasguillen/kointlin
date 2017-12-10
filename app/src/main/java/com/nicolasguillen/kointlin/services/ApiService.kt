package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.CoinPage
import com.nicolasguillen.kointlin.services.reponses.CoinPreview
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/map")
    fun getSupportedCoins(): Flowable<Response<List<CoinPreview>>>

    @GET("/page/{coin}")
    fun getPageFromCoin(@Path("coin") coin: String): Flowable<Response<CoinPage>>
}
