package com.syamil.izzat.goldappsample.domain

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.syamil.izzat.goldappsample.data.model.BaseRate
import com.syamil.izzat.goldappsample.data.model.Rate
import com.syamil.izzat.goldappsample.data.model.Response
import com.syamil.izzat.goldappsample.data.repository.RateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val rateRepository: RateRepository
) : ViewModel() {

    private val decimalFormat = DecimalFormat("#,###.##").also { it.roundingMode = RoundingMode.CEILING }

    private val mutableResponse = MutableStateFlow<Response<BaseRate>>(Response.Idle())
    val response : StateFlow<Response<BaseRate>> get() = mutableResponse

    private val mutableRates = MutableStateFlow<List<Rate>>(emptyList())
    val rates : StateFlow<List<Rate>> get() = mutableRates

    var mutableGram = MutableStateFlow(1.0)
    set(value) {
        field.value = if (value.value < 1.0) 1.0 else value.value
        field = value
    }

    fun loadRates() = viewModelScope.launch {
        mutableResponse.apply {
            if (value !is Response.Loading) {
                value = Response.Loading()
                value = try {
                    rateRepository.fetchRates().let {
                        if (it == null)
                            Response.Error()
                        else {
                            val (_, _, rates) = it.data
                            mutableRates.value = listOf(
                                Rate(0, "USD", rates.USD),  Rate(1, "AUD", rates.AUD), Rate(2, "BAM", rates.BAM),
                                Rate(3, "CAD", rates.CAD),  Rate(4, "DJF", rates.DJF), Rate(5, "EGP", rates.EGP),
                                Rate(6, "FJD", rates.FJD),  Rate(7, "GBP", rates.GBP), Rate(8, "HKD", rates.HKD),
                                Rate(9, "IDR", rates.IDR),  Rate(10, "JPY", rates.JPY), Rate(11, "KES", rates.KES),
                                Rate(12, "LAK", rates.LAK),  Rate(13, "MYR", rates.MYR), Rate(14, "NAD", rates.NAD),
                                Rate(15, "OMR", rates.OMR),  Rate(16, "PAB", rates.PAB), Rate(17, "QAR", rates.QAR),
                                Rate(18, "RON", rates.RON),  Rate(19, "SGD", rates.SGD), Rate(20, "THB", rates.THB),
                                Rate(21, "UAH", rates.UAH),  Rate(22, "WST", rates.WST), Rate(23, "XAF", rates.XAF),
                                Rate(24, "YER", rates.YER),  Rate(25, "ZAR", rates.ZAR), Rate(26, "BTC", rates.BTC),
                                Rate(27, "BCH", rates.BCH),  Rate(28, "ETH", rates.ETH)
                            )

                            Response.Success(it.data)
                        }
                    }
                } catch (e: Exception) {
                    Response.Error()
                }
            }
        }
    }

    fun formatPrice(value: BigDecimal) = try {
        decimalFormat.format(value)
    } catch (e: Exception) {
        "-"
    }

}