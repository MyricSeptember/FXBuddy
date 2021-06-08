package com.example.fxbuddy.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fxbuddy.domain.currentExchangeRate.FetchCurrentExchangeRateUseCase
import com.example.fxbuddy.domain.exchangeRateHistory.FetchExchangeRateHistoryUseCase
import com.example.fxbuddy.model.RateData
import com.example.fxbuddy.model.RateHistoryData
import kotlinx.coroutines.launch

class FXViewModel @ViewModelInject constructor(
    private val fetchCurrentExchangeRateUseCase: FetchCurrentExchangeRateUseCase,
    private val fetchExchangeRateHistoryUseCase: FetchExchangeRateHistoryUseCase
) : ViewModel() {

    var selectedFromCurrency: MutableLiveData<String>? = null
    var selectedToCurrency: MutableLiveData<String>? = null

//    val isLoading: LiveData<Boolean> = loadSessionsResult.map {
//        it == Result.Loading
//    }


    fun getExchangeRate() {

        viewModelScope.launch {
            val rateHistoryData = RateHistoryData("", "", "", "USDZAR", "")

            fetchExchangeRateHistoryUseCase(rateHistoryData)

//            val rate = RateData("USDZAR,ZARUSD")
//            val data = fetchCurrentExchangeRateUseCase(rate)
//

//            selectedFromCurrency?.value?.let { from ->
//                selectedToCurrency?.value?.let { to -> RateData(from + to) }
//            }?.also { rate ->
//                val data = fetchCurrentExchangeRateUseCase(rate)
//            }
        }
    }
}