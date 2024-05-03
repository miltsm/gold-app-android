package com.syamil.izzat.goldappsample.view.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.syamil.izzat.goldappsample.databinding.DialogSuccessfulTopupBinding

class SuccessTopUpDialog : DialogFragment() {

    private var _binding : DialogSuccessfulTopupBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = DialogSuccessfulTopupBinding.inflate(
        inflater, container, false
    ).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.okBtn.setOnClickListener { dismiss() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}