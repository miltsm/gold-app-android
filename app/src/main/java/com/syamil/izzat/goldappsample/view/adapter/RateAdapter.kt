package com.syamil.izzat.goldappsample.view.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.syamil.izzat.goldappsample.data.model.Rate
import com.syamil.izzat.goldappsample.databinding.VhRateBinding
import com.syamil.izzat.goldappsample.domain.HomeViewModel
import java.math.RoundingMode

class RateAdapter constructor(
    val vm: HomeViewModel
) : ListAdapter<Rate, RateAdapter.RateViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<Rate>() {
            override fun areItemsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem.index == newItem.index
            override fun areContentsTheSame(oldItem: Rate, newItem: Rate): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder = VhRateBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    ).let {
        RateViewHolder(it)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RateViewHolder(private val binding: VhRateBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            val rate = getItem(position)
            val price = rate.rateValue
                //?.times(28.34952)
                ?.times(vm.response.value.data?.goldPricePerGrams ?: 0.0)
                ?.toBigDecimal()
            binding.apply {
                rateTv.text = rate.name
                rateValue.text = vm.formatPrice(price!!) ?: "-"
            }
        }
    }
}