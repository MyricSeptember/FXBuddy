package com.example.fxbuddy.network.model

data class RateResponse(
    val from: String,
    val price: Double,
    val timestamp: Int,
    val to: String,
    val total: Double
)