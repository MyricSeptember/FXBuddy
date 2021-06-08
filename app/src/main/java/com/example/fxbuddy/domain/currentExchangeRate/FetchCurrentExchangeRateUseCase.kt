package com.example.fxbuddy.domain.currentExchangeRate

import com.example.fxbuddy.data.repository.FXRepository
import com.example.fxbuddy.di.IoDispatcher
import com.example.fxbuddy.domain.UseCase
import com.example.fxbuddy.model.RateData
import com.example.fxbuddy.model.RateResponse
import com.example.fxbuddy.result.Result
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class FetchCurrentExchangeRateUseCase @Inject constructor(
    private val repository: FXRepository,
    @IoDispatcher ioDispatcher: CoroutineDispatcher
) : UseCase<RateData, RateResponse>(ioDispatcher) {

    override suspend fun execute(parameters: RateData): RateResponse {
        return repository.getForeignExchangeRate(parameters)
    }
}
