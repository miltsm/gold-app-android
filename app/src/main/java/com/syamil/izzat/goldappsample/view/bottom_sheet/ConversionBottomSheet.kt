package com.syamil.izzat.goldappsample.view.bottom_sheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.syamil.izzat.goldappsample.databinding.FragmentConversionBinding
import com.syamil.izzat.goldappsample.view.BaseFragment

class ConversionBottomSheet : BaseFragment() {

    private var _binding : FragmentConversionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentConversionBinding.inflate(
        inflater, container, false
    ).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.apply {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}