package com.syamil.izzat.goldappsample.data.repository

import android.util.Log
import com.syamil.izzat.goldappsample.network.MetalApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RateRepository @Inject constructor(
    private val metalApi: MetalApi
) {
    private val ioDispatcher by lazy { Dispatchers.IO }

    suspend fun fetchRates() = coroutineScope {
        withContext(ioDispatcher) {
            metalApi.getRates()
        }
    }
}