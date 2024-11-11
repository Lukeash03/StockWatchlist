package com.luke.stockwatchlist.di

import com.luke.stockwatchlist.data.csv.CSVParser
import com.luke.stockwatchlist.data.csv.CompanyListingsParser
import com.luke.stockwatchlist.data.csv.IntraDayInfoParser
import com.luke.stockwatchlist.data.repository.StockRepositoryImpl
import com.luke.stockwatchlist.domain.model.CompanyListing
import com.luke.stockwatchlist.domain.model.IntraDayInfo
import com.luke.stockwatchlist.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntraDayInfoParser(
        intraDayInfoParser: IntraDayInfoParser
    ): CSVParser<IntraDayInfo>

    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository

}