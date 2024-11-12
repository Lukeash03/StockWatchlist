package com.luke.stockwatchlist.ui.companyDetailed

import com.luke.stockwatchlist.domain.model.CompanyInfo
import com.luke.stockwatchlist.domain.model.IntraDayInfo

data class CompanyInfoState(
    val stockInfos: List<IntraDayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
