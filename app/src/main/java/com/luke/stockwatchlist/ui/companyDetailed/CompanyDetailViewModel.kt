package com.luke.stockwatchlist.ui.companyDetailed

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luke.stockwatchlist.domain.repository.StockRepository
import com.luke.stockwatchlist.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
    private val repository: StockRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state = MutableStateFlow(CompanyInfoState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            state.value = state.value.copy(isLoading = true)
            val companyInfoResult = async { repository.getCompanyInfo(symbol) }
            val intraDayInfoResult = async { repository.getIntraDayInfo(symbol) }
            when(val result = companyInfoResult.await()) {
                is Resource.Success -> {
                    Log.i("CompanyDetailViewModel", "Company symbol: $symbol")
                    Log.i("CompanyDetailViewModel", "Company info result: ${result.data}")
                    state.value = state.value.copy(
                        companyInfo = result.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    Log.d("CompanyDetailViewModel", "Company info result: ${result.message}")
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message,
                        companyInfo = null
                    )
                }
                else -> Unit
            }
            when(val result = intraDayInfoResult.await()) {
                is Resource.Success -> {
                    state.value = state.value.copy(
                        stockInfos = result.data ?: emptyList(),
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    state.value = state.value.copy(
                        isLoading = false,
                        error = result.message,
                        companyInfo = null
                    )
                }
                else -> Unit
            }
        }
    }

}