package com.syamil.izzat.goldappsample.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.syamil.izzat.goldappsample.R
import com.syamil.izzat.goldappsample.data.model.Response
import com.syamil.izzat.goldappsample.databinding.FragmentHomeBinding
import com.syamil.izzat.goldappsample.domain.HomeViewModel
import com.syamil.izzat.goldappsample.view.adapter.RateAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private var _binding : FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeAVM by activityViewModels<HomeViewModel>()

    private val rateAdapter by lazy { RateAdapter(homeAVM) }

    override fun onStart() {
        super.onStart()
        viewLifecycleOwner.lifecycleScope.launch {
            homeAVM.rates.collectLatest { rateAdapter.submitList(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentHomeBinding.inflate(
        inflater, container, false
    ).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {
            swipeRefresh.apply {
                setColorSchemeColors(resources.getColor(R.color.teal_700, null))
                setOnRefreshListener { homeAVM.loadRates() }
                viewLifecycleOwner.lifecycleScope.launch {
                    homeAVM.response.collectLatest {
                        isRefreshing = it is Response.Loading
                        if (it is Response.Error)
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(),3,  GridLayoutManager.VERTICAL, false)
                adapter = rateAdapter
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}