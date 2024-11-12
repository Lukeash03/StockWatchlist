package com.luke.stockwatchlist.domain.repository

import com.luke.stockwatchlist.domain.model.CompanyInfo
import com.luke.stockwatchlist.domain.model.CompanyListing
import com.luke.stockwatchlist.domain.model.IntraDayInfo
import com.luke.stockwatchlist.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntraDayInfo(
        symbol: String
    ): Resource<List<IntraDayInfo>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Resource<CompanyInfo>

    suspend fun addToWatchlist(
        symbol: String
    )

    suspend fun removeFromWatchlist(
        symbol: String
    )

}