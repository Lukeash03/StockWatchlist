package com.luke.stockwatchlist.ui.companyListing

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.luke.stockwatchlist.R
import com.luke.stockwatchlist.databinding.FragmentCompanyListingsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CompanyListingsFragment : Fragment() {
    private val companyListingsViewModel: CompanyListingsViewModel by viewModels()

    private var _binding: FragmentCompanyListingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var stockRecyclerView: RecyclerView

    private val TAG = "CompanyListingsFragment"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCompanyListingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        companyListingsViewModel.getCompanyListings()
        Log.i(TAG, "Data: ${companyListingsViewModel.state.value.companies}")

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                companyListingsViewModel.state.collect() { state ->
                    if (state.companies.isNotEmpty()) {
                        stockRecyclerView.adapter = CompanyListingsAdapter(state.companies)
                    }
                }
            }
        }

        stockRecyclerView = root.findViewById(R.id.stockHomeRecyclerView)
        stockRecyclerView.layoutManager = LinearLayoutManager(this.context)
        stockRecyclerView.adapter = CompanyListingsAdapter(emptyList())

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}