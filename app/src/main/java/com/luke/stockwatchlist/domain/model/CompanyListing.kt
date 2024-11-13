package com.luke.stockwatchlist.domain.model

data class CompanyListing(
    val name: String,
    val symbol: String,
    val exchange: String,
    val price: String? = null,
//    var isWatchlisted: Boolean = false
)
