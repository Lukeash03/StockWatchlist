package com.luke.stockwatchlist.ui.companyListing

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.databinding.FragmentCompanyListingsBinding
import com.luke.stockwatchlist.ui.companyDetailed.CompanyDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListingsFragment : Fragment() {
    private var _binding: FragmentCompanyListingsBinding? = null

    private val binding get() = _binding!!

    private val companyListingsViewModel: CompanyListingsViewModel by viewModels()
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var stockRecyclerView: RecyclerView

    private val TAG = "CompanyListingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyListingsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        swipeRefreshLayout = binding.swipeRefreshLayout

        swipeRefreshLayout.setOnRefreshListener {
            companyListingsViewModel.onEvent(CompanyListingsEvent.Refresh)
        }

        companyListingsViewModel.getCompanyListings()
        Log.i(TAG, "Data: ${companyListingsViewModel.state.value.companies}")

        stockRecyclerView = root.findViewById(R.id.stockHomeRecyclerView)
        stockRecyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = CompanyListingsAdapter(
            emptyList(),
            onAddToWatchlistClick = { symbol ->
                companyListingsViewModel.addToWatchlist(symbol)
                Toast.makeText(this.context, "$symbol added to watchlist.", Toast.LENGTH_LONG).show()
            },
//            onRemoveFromWatchlistClick = { symbol ->
//                companyListingsViewModel.removeFromWatchlist(symbol)
//            }
        )
        stockRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : CompanyListingsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
//                Log.i(TAG, "Clicked on position: $position")
                val intent = Intent(requireContext(), CompanyDetailActivity::class.java)
                intent.putExtra(
                    "symbol",
                    companyListingsViewModel.state.value.companies[position].symbol
                )
                startActivity(intent)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                companyListingsViewModel.state.collect { state ->
                    if (state.companies.isNotEmpty()) {
                        adapter.updateData(state.companies)
                    }
                }
            }
        }

        binding.tilCLSearch.editText?.addTextChangedListener { editable ->
            val query = editable.toString()
            companyListingsViewModel.onEvent(CompanyListingsEvent.OnSearchQueryChange(query))
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}