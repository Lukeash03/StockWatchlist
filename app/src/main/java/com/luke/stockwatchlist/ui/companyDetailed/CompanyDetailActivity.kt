package com.luke.stockwatchlist.ui.companyDetailed

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.domain.model.CompanyInfo
import com.luke.stockwatchlist.domain.model.IntraDayInfo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CompanyDetailActivity : AppCompatActivity() {
    private val companyDetailViewModel: CompanyDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_company_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.company_detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bundle: Bundle? = intent.extras
        val symbol = bundle!!.getString("symbol")

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                companyDetailViewModel.state.collect { state ->
                    val stockPrice = state.stockInfos.maxByOrNull { it.date.hour }
                    bindValue(state.companyInfo, stockPrice)
                    if (state.error != null) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@CompanyDetailActivity, "Error loading company information.", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
        Log.i("CompanyDetailActivity", "Data: ${companyDetailViewModel.state.value.companyInfo}")
    }

    private fun bindValue(companyInfo: CompanyInfo?, stockInfo: IntraDayInfo?) {
        val companyName = findViewById<TextView>(R.id.tvCompanyName)
        val companySymbol = findViewById<TextView>(R.id.tvCompanySymbol)
        val industry = findViewById<TextView>(R.id.tvIndustry)
        val country = findViewById<TextView>(R.id.tvCountry)
        val description = findViewById<TextView>(R.id.tvDescription)
        val stockPrice = findViewById<TextView>(R.id.tvStockPrice)

        companyName.text = companyInfo?.name
        companySymbol.text = companyInfo?.symbol
        industry.text = "Industry: ${companyInfo?.industry}"
        country.text = "Country: ${companyInfo?.country}"
        description.text = "Description: ${companyInfo?.description}"
        stockPrice.text = "Stock price: ${stockInfo?.close}"
    }
}