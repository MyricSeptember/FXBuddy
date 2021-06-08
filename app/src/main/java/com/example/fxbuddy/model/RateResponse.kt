package com.example.fxbuddy.model

data class RateResponse(
    val date: String,
    val price: Map<String, Double>
)