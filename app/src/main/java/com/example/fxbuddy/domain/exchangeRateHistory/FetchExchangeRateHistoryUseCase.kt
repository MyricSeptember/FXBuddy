package com.example.fxbuddy.domain.exchangeRateHistory

import com.example.fxbuddy.data.repository.FXRepository
import com.example.fxbuddy.di.IoDispatcher
import com.example.fxbuddy.domain.UseCase
import com.example.fxbuddy.model.RateHistoryData
import com.example.fxbuddy.model.RateHistoryResponse
import com.example.fxbuddy.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FetchExchangeRateHistoryUseCase @Inject constructor(
    private val repository: FXRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<RateHistoryData, Result<RateHistoryResponse>>(ioDispatcher) {

    override suspend fun execute(parameters: RateHistoryData): Result<RateHistoryResponse> {
        return repository.getForeignExchangeRateHistory(parameters)
    }
}