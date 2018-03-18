package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("v1/ticker/")
    fun getTopCoins(): Single<Response<List<TopCoin>>>

    @GET("v1/ticker/{id}")
    fun getCoinFromId(@Path("id") id: String): Single<Response<List<TopCoin>>>

}
