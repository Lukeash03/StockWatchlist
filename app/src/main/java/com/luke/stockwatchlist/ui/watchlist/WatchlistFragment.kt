package com.luke.stockwatchlist.ui.watchlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.databinding.FragmentWatchlistBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class WatchlistFragment : Fragment() {

    private var _binding: FragmentWatchlistBinding? = null
    private val binding get() = _binding!!

    private val watchlistViewModel: WatchlistViewModel by viewModels()
    private lateinit var watchlistRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWatchlistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.swipeRefreshLayout.setOnRefreshListener {

        }

        watchlistRecyclerView = root.findViewById(R.id.stockWatchlistRecyclerView)
        watchlistRecyclerView.layoutManager = LinearLayoutManager(this.context)
        val adapter = WatchlistAdapter(
            emptyList(),
            onAddToWatchlistClick = { symbol ->
                watchlistViewModel.removeFromWatchlist(symbol)
            }
        )
        watchlistRecyclerView.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            watchlistViewModel.watchlistedCompanies.collect { companies ->
                adapter.updateData(companies)
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}