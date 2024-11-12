package com.luke.stockwatchlist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT *
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
            UPPER(:query) == symbol
        """
    )
    suspend fun searchCompanyListings(query: String): List<CompanyListingEntity>

    // Methods for watchlist support
    @Query("SELECT * FROM companylistingentity WHERE isWatchlisted = 1")
    suspend fun getWatchlistedCompanies(): List<CompanyListingEntity>

    @Query("UPDATE companylistingentity SET isWatchlisted = 1 WHERE symbol = :symbol")
    suspend fun addCompanyToWatchlist(symbol: String)

    @Query("UPDATE companylistingentity SET isWatchlisted = 0 WHERE symbol = :symbol")
    suspend fun removeCompanyFromWatchlist(symbol: String)

    @Query("SELECT * FROM companylistingentity WHERE symbol = :symbol")
    suspend fun getCompanyBySymbol(symbol: String): CompanyListingEntity?

}