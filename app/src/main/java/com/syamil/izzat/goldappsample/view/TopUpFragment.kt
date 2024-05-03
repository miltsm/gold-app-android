package com.syamil.izzat.goldappsample.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syamil.izzat.goldappsample.R
import com.syamil.izzat.goldappsample.databinding.FragmentTopUpBinding
import com.syamil.izzat.goldappsample.domain.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopUpFragment : DialogFragment() {

    private var _binding: FragmentTopUpBinding? = null
    private val binding get() = _binding!!

    private val homeAVM by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentTopUpBinding.inflate(
        inflater, container, false
    ).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

            viewLifecycleOwner.lifecycleScope.launch {
                homeAVM.mutableGram.collectLatest {
                    warningTv.apply {
                        text = getString(R.string.warning).replace(
                            "0.00", homeAVM.formatPrice(
                                it.times(homeAVM.response.value.data?.goldPricePerGrams ?: 62.29).toBigDecimal()
                            ), true
                        ).replace("0", "$it g", true)
                    }
                }
            }

            negativeBtn.setOnClickListener { findNavController().navigateUp() }
            positiveBtn.setOnClickListener {
                findNavController().navigate(R.id.action_topUpFragment_to_successTopUpDialog)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}