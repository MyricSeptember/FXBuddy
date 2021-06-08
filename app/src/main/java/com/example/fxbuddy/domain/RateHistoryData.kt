package com.example.fxbuddy.domain

data class RateHistoryData(
    val startDate: String,
    val endDate: String,
    val interval: String,
    val currency: String,
    val format: String
)