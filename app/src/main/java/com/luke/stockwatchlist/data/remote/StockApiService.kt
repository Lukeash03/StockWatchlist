package com.luke.stockwatchlist.data.remote

import com.luke.stockwatchlist.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApiService {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = getApiKey()
    ): ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntraDayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = getApiKey()
    ): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = getApiKey()
    ): CompanyInfoDto

    companion object {
        private fun getApiKey(): String {
            return System.getenv("API_KEY") ?: ""
        }
        const val BASE_URL = "https://alphavantage.co"
    }
}
