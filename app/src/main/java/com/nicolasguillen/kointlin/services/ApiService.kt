package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.CoinPreview
import io.reactivex.Flowable
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/map")
    fun getSupportedCoins(): Flowable<Response<List<CoinPreview>>>
}
