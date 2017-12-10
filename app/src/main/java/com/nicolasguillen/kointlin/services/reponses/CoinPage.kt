package com.nicolasguillen.kointlin.services.reponses

import com.google.gson.annotations.SerializedName

class CoinPage(@SerializedName("id")
               val id: String,
               @SerializedName("price_usd")
               val price: Double)