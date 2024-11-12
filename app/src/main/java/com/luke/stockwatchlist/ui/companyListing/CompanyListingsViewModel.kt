package com.luke.stockwatchlist.ui.companyListing

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luke.stockwatchlist.domain.repository.StockRepository
import com.luke.stockwatchlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    val state = MutableStateFlow(CompanyListingsState())

    private var searchJob: Job? = null

    fun onEvent(event: CompanyListingsEvent) {
        when(event) {
            CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                state.value = state.value.copy(searchQuery = event.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
    }

    fun getCompanyListings(
        fetchFromRemote: Boolean = false,
        query: String = state.value.searchQuery.lowercase()
    ) {
        viewModelScope.launch {
            repository
                .getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when(result) {
                        is Resource.Error -> {
                            Log.e("CompanyListingsViewModel", "Error: ${result.message}")
                        }
                        is Resource.Loading -> {
                            state.value = state.value.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                Log.i("CompanyListingsViewModel", "$listings")
                                state.value = state.value.copy(
                                    companies = listings
                                )
                            }
                        }
                    }
                }
        }
    }

    fun addToWatchlist(symbol: String) {
        viewModelScope.launch {
            repository.addToWatchlist(symbol)
        }
    }

    fun removeFromWatchlist(symbol: String) {
        viewModelScope.launch {
            repository.removeFromWatchlist(symbol)
        }
    }

}