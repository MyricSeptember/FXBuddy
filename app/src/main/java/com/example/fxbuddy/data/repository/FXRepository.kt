package com.example.fxbuddy.data.repository

import com.example.fxbuddy.model.RateData
import com.example.fxbuddy.model.RateHistoryData
import com.example.fxbuddy.model.RateHistoryResponse
import com.example.fxbuddy.model.RateResponse
import com.example.fxbuddy.result.Result

interface FXRepository {
    suspend fun getForeignExchangeRate(ratesData: RateData): RateResponse
    suspend fun getForeignExchangeRateHistory(ratesData: RateHistoryData): Result<RateHistoryResponse>
}