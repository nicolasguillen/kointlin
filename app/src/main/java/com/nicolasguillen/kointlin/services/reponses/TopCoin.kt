package com.nicolasguillen.kointlin.services.reponses

import com.google.gson.annotations.SerializedName

class TopCoin(val id: String,
              val name: String,
              val symbol: String,
              @SerializedName(value="price_usd", alternate=["price_eur", "price_jpy", "price_gbp", "price_aud", "price_chf"])
              val price: String,
              @SerializedName("percent_change_24h")
              val percentChange24h: String
)