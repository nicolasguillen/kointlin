@file:Suppress("OVERLOADS_WITHOUT_DEFAULT_ARGUMENTS")

package com.nicolasguillen.kointlin.services.reponses

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
data class CoinTelegraphFeed @JvmOverloads constructor(
        @field:Attribute
        var version: String? = null,
        @field:Element(name = "channel")
        var channel: CoinTelegraphChannel? = null
)

@Root(name = "channel", strict = false)
data class CoinTelegraphChannel @JvmOverloads constructor(
        @field:ElementList(inline = true, required=false)
        var feedItems: List<CoinTelegraphItem>? = null
)

@Root(name = "item", strict = false)
data class CoinTelegraphItem @JvmOverloads constructor(
        @field:Element(name="title", required = false)
        var title: String? = null,
        @field:Element(name="link", required = false)
        var link: String? = null,
        @field:Element(name="content", required = false)
        var content: CoinTelegraphMedia? = null
)

@Root(name = "content", strict = false)
data class CoinTelegraphMedia @JvmOverloads constructor(
        @field:Attribute
        var url: String? = null
)