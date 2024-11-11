package com.luke.stockwatchlist.ui.companyListing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.domain.model.CompanyListing

class CompanyListingsAdapter(
    private val stockList: List<CompanyListing>,
//    private val onItemClick: (String) -> Unit
) :
RecyclerView.Adapter<CompanyListingsAdapter.CompanyListingViewHolder>() {

    class CompanyListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompanyName: TextView = itemView.findViewById(R.id.tvCICompanyName)
        val tvCompanyExchange: TextView = itemView.findViewById(R.id.tvCICompanyExchange)
        val tvCompanySymbol: TextView = itemView.findViewById(R.id.tvCICompanySymbol)
//        val tvCompanyStockPrice: TextView = itemView.findViewById(R.id.tvCICompanyStockPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyListingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_company_listing, parent, false)
        return CompanyListingViewHolder(view)
    }

    override fun getItemCount(): Int = stockList.size

    override fun onBindViewHolder(holder: CompanyListingViewHolder, position: Int) {
        val stockItem = stockList[position]
        holder.tvCompanyName.text = stockItem.name
        holder.tvCompanyExchange.text = stockItem.exchange
        holder.tvCompanySymbol.text = stockItem.symbol
    }

}