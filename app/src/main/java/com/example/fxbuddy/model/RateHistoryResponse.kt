package com.example.fxbuddy.model

data class RateHistoryResponse(
    val start_date: String,
    val end_date: String,
    val price: Map<String, Map<String, Rate>>

)