package com.example.fxbuddy.domain

data class RateData(
    val fromCurrency: String,
    val toCurrency: String,
) {
    val amount: Int = 1
}