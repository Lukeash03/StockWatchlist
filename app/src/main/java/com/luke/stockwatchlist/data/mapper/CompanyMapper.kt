package com.luke.stockwatchlist.data.mapper

import com.luke.stockwatchlist.data.local.CompanyListingEntity
import com.luke.stockwatchlist.data.remote.dto.CompanyInfoDto
import com.luke.stockwatchlist.domain.model.CompanyInfo
import com.luke.stockwatchlist.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange,
        price = price
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange,
        price = price
    )
}

fun CompanyInfoDto.toCompanyInfo(): CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}