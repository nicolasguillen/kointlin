package com.nicolasguillen.kointlin.services.reponses

import com.google.gson.annotations.SerializedName

class TopCoin(val id: String,
              val name: String,
              val symbol: String,
              @SerializedName("price_usd")
              val priceUsd: String,
              @SerializedName("percent_change_24h")
              val percentChange24h: String
)