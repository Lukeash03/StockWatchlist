package com.luke.stockwatchlist.ui.companyListing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.domain.model.CompanyListing

class CompanyListingsAdapter(
    private var stockList: List<CompanyListing>,
    private val onAddToWatchlistClick: (String) -> Unit, // Lambda function for adding to watchlist
//    private val onRemoveFromWatchlistClick: (String) -> Unit // Lambda function for removing from watchlist
) :
RecyclerView.Adapter<CompanyListingsAdapter.CompanyListingViewHolder>() {

    private var mListener: OnItemClickListener? = null
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    class CompanyListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompanyName: TextView = itemView.findViewById(R.id.tvCICompanyName)
        val tvCompanyExchange: TextView = itemView.findViewById(R.id.tvCICompanyExchange)
        val tvCompanySymbol: TextView = itemView.findViewById(R.id.tvCICompanySymbol)
        val ibAddToWatchlist: ImageButton = itemView.findViewById(R.id.imageButton)

        fun bind(listener: OnItemClickListener?, position: Int, onAddClick: (String) -> Unit) {
            itemView.setOnClickListener {
                listener?.onItemClick(position)
            }

            ibAddToWatchlist.setOnClickListener {
                onAddClick(tvCompanySymbol.text.toString())
            }
        }
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

        holder.bind(mListener, position, onAddToWatchlistClick)
    }

    fun updateData(newStockList: List<CompanyListing>) {
        stockList = newStockList
        notifyDataSetChanged()
    }
}