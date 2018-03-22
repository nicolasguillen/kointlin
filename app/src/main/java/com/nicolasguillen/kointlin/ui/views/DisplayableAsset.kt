package com.nicolasguillen.kointlin.ui.views

import com.nicolasguillen.kointlin.storage.entities.Asset

class DisplayableAsset(
        val asset: Asset,
        val currentPrice: Double,
        val variant: Double,
        val currencyCode: String
)