package com.nicolasguillen.kointlin.services

import com.nicolasguillen.kointlin.services.reponses.CoinTelegraphFeed
import com.nicolasguillen.kointlin.services.reponses.CoindeskFeed
import com.nicolasguillen.kointlin.services.reponses.TopCoin
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/v1/ticker/")
    fun getTopCoins(@Query("convert") currency: String): Single<Response<List<TopCoin>>>

    @GET("/v1/ticker/{id}")
    fun getCoinFromId(@Path("id") id: String,
                      @Query("convert") currency: String): Single<Response<List<TopCoin>>>

    @GET("http://api.coindesk.com/mapi/posts.json")
    fun getCoindeskNewsFeed(): Single<Response<List<CoindeskFeed>>>

}

interface RssService {

    @GET("https://cointelegraph.com/feed/")
    fun getCoinTelegraphNewsFeed(): Single<Response<CoinTelegraphFeed>>

}
