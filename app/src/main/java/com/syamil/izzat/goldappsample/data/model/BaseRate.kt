package com.syamil.izzat.goldappsample.data.model

data class BaseRate(val base: String, val timestamp: String, val rates: RateList, val unit: String, val goldPricePerGrams: Double) {
    fun getPriceByGram(gram: Double) = goldPricePerGrams.times(gram).toBigDecimal()
}