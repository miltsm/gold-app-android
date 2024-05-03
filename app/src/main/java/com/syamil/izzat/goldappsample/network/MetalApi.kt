package com.syamil.izzat.goldappsample.network

import com.syamil.izzat.goldappsample.data.model.RateResponse
import retrofit2.http.GET

interface MetalApi {
    @GET("/api/metals/rates")
    suspend fun getRates() : RateResponse?
}