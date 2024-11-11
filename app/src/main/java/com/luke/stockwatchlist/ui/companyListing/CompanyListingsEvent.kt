package com.luke.stockwatchlist.ui.companyListing

sealed class CompanyListingsEvent {
    data object Refresh: CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String): CompanyListingsEvent()
}