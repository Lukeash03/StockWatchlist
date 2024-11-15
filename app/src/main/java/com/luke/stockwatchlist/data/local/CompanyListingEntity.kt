package com.luke.stockwatchlist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    val price: String? = null,
    var isWatchlisted: Boolean = false,
    @PrimaryKey val id: Int? = null
)