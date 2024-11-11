package com.luke.stockwatchlist.ui.companyDetailed

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.luke.stockwatchlist.R

class CompanyDetailFragment : Fragment() {

    companion object {
        fun newInstance() = CompanyDetailFragment()
    }

    private val viewModel: CompanyDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_company_detail, container, false)
    }
}