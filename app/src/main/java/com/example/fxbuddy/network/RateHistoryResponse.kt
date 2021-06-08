package com.example.fxbuddy.network

import com.example.fxbuddy.network.model.Rate

data class RateHistoryResponse(
    val start_date: String,
    val end_date: String,
    val price: Map<String, Map<String, Rate>>
)