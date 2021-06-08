package com.example.fxbuddy.data.repository

import android.util.Log
import com.example.fxbuddy.data.datasource.FxDataSource
import com.example.fxbuddy.model.RateData
import com.example.fxbuddy.model.RateHistoryData
import com.example.fxbuddy.model.RateHistoryResponse
import com.example.fxbuddy.model.RateResponse
import com.example.fxbuddy.result.Result
import com.example.fxbuddy.result.data

class DefaultFxRepository(private val fxDataSource: FxDataSource) : FXRepository {
    override suspend fun getForeignExchangeRate(ratesData: RateData): RateResponse {
        TODO("Not yet implemented")
    }

//    override suspend fun getForeignExchangeRate(rateData: RateData): Result<RateResponse> {
//       val dta  = fxDataSource.getRates("debZIn8tbhUjEevqtCbZ", "2021-02-02", "daily", rateData.currency).body()
//        val r :Result<RateResponse>? = null
//        return r!!
//    }

    override suspend fun getForeignExchangeRateHistory(ratesData: RateHistoryData): Result<RateHistoryResponse> {

        val dta  = fxDataSource.getRatesHistory("NkjUiK7oSLH7HwIEy1uo", "2018-07-02","2018-09-03",  "daily","ZARUSD","ohlc").body()
        val close = dta?.price?.entries?.first()?.value?.entries?.first()?.value?.close
        Log.d("CLOSE PRICE",close.toString())
        val r :Result<RateHistoryResponse>? = null
        return r!!
        }


    }
