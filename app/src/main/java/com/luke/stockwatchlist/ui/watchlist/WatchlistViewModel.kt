package com.luke.stockwatchlist.ui.watchlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luke.stockwatchlist.data.local.CompanyListingEntity
import com.luke.stockwatchlist.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    private val _watchlistedCompanies = MutableStateFlow<List<CompanyListingEntity>>(emptyList())
    val watchlistedCompanies: StateFlow<List<CompanyListingEntity>> = _watchlistedCompanies

    init {
        getWatchlistedCompanies()
    }

    private fun getWatchlistedCompanies() {
        viewModelScope.launch {
            val companies = repository.getWatchlistedCompanies()
            _watchlistedCompanies.value = companies
        }
    }

    fun removeFromWatchlist(symbol: String) {
        viewModelScope.launch {
            repository.removeFromWatchlist(symbol)
            getWatchlistedCompanies()
        }
    }

}